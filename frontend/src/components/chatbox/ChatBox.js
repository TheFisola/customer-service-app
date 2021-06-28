import React, { Component } from 'react';
import './ChatBox.css';
import SockJS from 'sockjs-client/dist/sockjs';
import * as Stomp from 'stompjs';
import { Alert } from 'bootstrap';

class ChatBox extends Component {
  constructor(props) {
    super(props);
    this.stompClient = null;
    // get login user from local storage
    this.state = {
      anotherAgentWorkingOnIt: props.anotherAgentWorkingOnIt,
      agentIdOfLoggedInUser: props.agentIdOfLoggedInUser,
      customerConversations: [],
      customerRequest: props.customerRequest,
      loginRole: props.loginRole == 'user' ? 'CUSTOMER' : 'AGENT',
      requestFinalized: props.customerRequest.status == 'FINALISED_REQUEST',
    };
  }

  async componentDidMount() {
    this.connect();
    this.addSendMessageOnEnterListener();
    await fetch(
      `${process.env.REACT_APP_BASE_URL}/api/customer-request-conversations?customerRequestId=${this.state.customerRequest.id}`
    )
      .then((response) => response.json())
      .then((data) =>
        this.setState({
          customerConversations: data,
        })
      );
  }

  connect() {
    const socket = new SockJS(`${process.env.REACT_APP_WS_URL}/chat`);
    this.stompClient = Stomp.over(socket);
    this.stompClient.connect({}, () => {
      const { customerRequest, customerConversations } = this.state;
      this.stompClient.subscribe(
        '/topic/messages/' + customerRequest.id,
        (messageOutput) => {
          const newConversationEntry = JSON.parse(messageOutput.body);
          this.updateChatState(customerConversations, newConversationEntry);
        }
      );
    });
  }

  componentWillUnmount() {
    if (this.stompClient != null) {
      this.stompClient.disconnect();
    }
  }

  updateChatState(customerConversations, newConversationEntry) {
    customerConversations.push(newConversationEntry);
    this.setState({ customerConversations: customerConversations });
  }

  addSendMessageOnEnterListener() {
    document
      .getElementById('text-message')
      .addEventListener('keyup', function (event) {
        if (event.key === 'Enter') {
          event.preventDefault();
          document.getElementById('sendMessage').click();
        }
      });
  }

  sendMessage() {
    const textMessage = document.getElementById('text-message');
    if (
      textMessage.value == '' ||
      textMessage.value == null ||
      textMessage.value == undefined
    ) {
      window.alert('Message Cannot Be Empty');
      return;
    }

    this.stompClient.send(
      '/app/chat/' + this.state.customerRequest.id,
      {},
      JSON.stringify({
        customerRequestId: this.state.customerRequest.id,
        message: textMessage.value,
        messageOwner: this.state.loginRole,
        messageOwnerId:
          this.state.loginRole == 'AGENT'
            ? this.state.agentIdOfLoggedInUser
            : this.state.customerRequest.user.id,
      })
    );
    textMessage.value = '';
  }

  render() {
    const {
      requestFinalized,
      customerConversations,
      loginRole,
      anotherAgentWorkingOnIt,
    } = this.state;

    const placeholder = anotherAgentWorkingOnIt
      ? '***Another agent is currently working on this request***'
      : requestFinalized
      ? '***Text field disabled as request has already being marked as finalized***'
      : 'Type your message here ...';

    const conversations = customerConversations.map((conversation) => {
      const isMessageOwner =
        loginRole == conversation.messageOwner ? 'chat__message-own' : '';

      return (
        <div
          key={conversation.id}
          className={`chat__message ${isMessageOwner}`}
        >
          {/* <div>{conversation.createdOn}</div> */}
          <div>{conversation.message}</div>
        </div>
      );
    });

    return (
      <div>
        <div className='chat'>
          <div className='chat__wrapper'>{conversations}</div>
        </div>
        <div className='chat__form'>
          <form id='chat__form'>
            <textarea
              id='text-message'
              type='text'
              rows='3'
              placeholder={placeholder}
              disabled={requestFinalized || anotherAgentWorkingOnIt}
            ></textarea>

            {!requestFinalized && (
              <button
                id='sendMessage'
                type='button'
                onClick={() => this.sendMessage()}
              >
                Send
              </button>
            )}
          </form>
        </div>
      </div>
    );
  }
}

export default ChatBox;
