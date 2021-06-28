import React, { Component } from 'react';
import { Link } from 'react-router-dom';
import ChatBox from '../chatbox/ChatBox';
import Header from '../header/Header';
import './CustomerChatUI.css';

//TODO: Move to env file and add agent to login flow

class CustomerChatUI extends Component {
  constructor(props) {
    super(props);
    this.state = {
      agent: {},
      customerRequest: {},
      customerConversations: [],
      loginRole: 'agent',
      openChatBox: false,
      loading: true,
    };
  }

  async componentDidMount() {
    const pathNameSplit = window.location.pathname.split('/');
  
    const customerRequestId = pathNameSplit[4];
    
    this.setState({ loginRole: pathNameSplit[1]});
    await fetch(
      `http://localhost:9090/api/customer-requests/${customerRequestId}`
    )
      .then((response) => response.json())
      .then((data) =>
        this.setState({
          customerRequest: data,
          openChatBox:
            data.status === 'ATTENDING_TO_REQUEST' ||
            data.status === 'FINALISED_REQUEST',
        })
      );

    try {
      const response = await fetch(
        `http://localhost:9090/api/customer-requests/${customerRequestId}/agent`
      );
      if (response.status === 200) {
        const data = await response.json();
        this.setState({
          agent: data,
        });
      }
    } catch (e) {
      console.log('Error getting agent ', e);
    }

    this.setState({ loading: false });
  }

  async updateAgentState(customerRequestId) {
    try {
      const response = await fetch(
        `http://localhost:9090/api/customer-requests/${customerRequestId}/agent`
      );
      if (response.status === 200) {
        const data = await response.json();
        this.setState({
          agent: data,
        });
      }
    } catch (e) {
      console.log('Error getting agent ', e);
    }
  }

  async attendToCustomerRequest(customerRequestId) {
    const loggedAgent = JSON.parse(localStorage.getItem('LOGGED_IN_AGENT'));
    this.setState({ loading: true });
    await fetch(`http://localhost:9090/api/customer-requests/attend`, {
      method: 'PUT',
      body: JSON.stringify({
        agentId: loggedAgent.id,
        customerRequestId: customerRequestId,
      }),
      headers: {
        Accept: 'application/json',
        'Content-Type': 'application/json',
      },
    })
      .then((response) => response.json())
      .then((data) => {
        this.updateAgentState(customerRequestId);
        this.setState({
          loading: false,
          customerRequest: data,
          agent: loggedAgent,
          openChatBox:
            data.status === 'ATTENDING_TO_REQUEST' ||
            data.status === 'FINALISED_REQUEST',
        });
      });
  }

  async markRequestAsFinalized(customerRequestId) {
    this.setState({ loading: true });

    await fetch(
      `http://localhost:9090/api/customer-requests/${customerRequestId}/finalise`,
      {
        method: 'PUT',
        headers: {
          Accept: 'application/json',
          'Content-Type': 'application/json',
        },
      }
    )
      .then((response) => response.json())
      .then((data) =>
        this.setState({
          loading: false,
          customerRequest: data,
          openChatBox:
            data.status === 'ATTENDING_TO_REQUEST' ||
            data.status === 'FINALISED_REQUEST',
        })
      );
  }

  render() {
    const { customerRequest, openChatBox, loginRole, agent, loading } =
      this.state;

    if (loading) {
      return <img src={window.location.origin + '/img/loader.gif'}></img>;
    }

    const agentIdOfLoggedInUser = JSON.parse(localStorage.getItem('LOGGED_IN_AGENT')).id;
    const anotherAgentWorkingOnIt =  customerRequest.status === 'ATTENDING_TO_REQUEST' && (agent && agent.id !== agentIdOfLoggedInUser);

    console.log('AGENT ID', agentIdOfLoggedInUser);

    const status = customerRequest.status
      ? customerRequest.status?.replaceAll('_', ' ')
      : '';

    const secondCard = openChatBox ? (
      <div className='card text-center w-45 h-50'>
        <div className='card-body'>
          <h5 className='card-title'>
            Chat Box  {this.state.loginRole == 'agent' && (
            <a
              href={
                window.location.origin +
                window.location.pathname.replace('agent', 'user')
              }
              target='_blank'
            >
              <small className='float-right'>Customer's View</small>
            </a>
            )}
          </h5>
          <ChatBox
            agentIdOfLoggedInUser={agentIdOfLoggedInUser}
            customerRequest={customerRequest}
            loginRole={loginRole}
            anotherAgentWorkingOnIt={anotherAgentWorkingOnIt}
          ></ChatBox>
        </div>
      </div>
    ) : (
      <div className='card text-center w-45 h-50'>
        <div className='card-body h-n'>
          <h5 className='card-title'>Chat Box</h5>
          <p className='card-text pt-5 pt-5'>
            This customer request is awaiting response
          </p>
          <a
            className='btn btn-dark'
            onClick={() => this.attendToCustomerRequest(customerRequest.id)}
          >
            Attend To Customer Request
          </a>
        </div>
      </div>
    );

    return (
      <div>
        <Header></Header>
        <div className='d-flex justify-content-center'>
          <div className='card w-45 h-50'>
            <div className='card-body h-n'>
              <h5 className='card-title'>Customer Request</h5>
              <p className='card-text'>
                <strong>{customerRequest.user?.name}</strong>
              </p>
              <p className='card-text'>
                <strong>Message:</strong>{' '}
                <span className='font-italic'>{customerRequest.message}</span>
              </p>
              <p className='card-text'>
                <strong>Status:</strong> {status}
              </p>
              <p className='card-text'>
                <strong>Type:</strong> {customerRequest.type}
              </p>

              <p className='card-text'>
                <strong>Agent:</strong>{' '}
                <span className='font-italic'>
                  {agent.name
                    ? agent.name
                    : '*No agent has attended to this request yet*'}
                </span>
              </p>

              <p className='card-text'>
                <strong>Date:</strong> {customerRequest.createdOn}
              </p>

              {this.state.loginRole == 'agent' &&
                customerRequest.status === 'ATTENDING_TO_REQUEST' && (
                  
                    <a
                      onClick={() =>
                        this.markRequestAsFinalized(customerRequest.id)
                      }
                      className='btn btn-dark'
                    >
                      Mark Request As Finalized
                    </a>
                )}
                    {this.state.loginRole == 'agent' && (

                    <Link
                      to={'/' + this.state.loginRole + '/customer/requests'}
                      className='btn btn-primary ml-2'
                    >
                      Go Back
                    </Link>
                
                )}
            </div>
          </div>

          {secondCard}
        </div>
      </div>
    );
  }
}

export default CustomerChatUI;
