import React, { Component } from 'react';
import Header from '../header/Header';
import './CustomerRequest.css';
import Pagination from 'react-js-pagination';

class CustomerRequest extends Component {
  constructor(props) {
    super(props);
    this.state = {
      loading: true,
      customerRequests: [],
      activePage: 1,
      totalItemsCount: 0,
      pageSize: 5,
    };
  }

  async componentDidMount() {
    await fetch(
      `http://localhost:9090/api/customer-requests?pageSize=${this.state.pageSize}`
    )
      .then((response) => response.json())
      .then((data) =>
        this.setState({
          loading:false,
          customerRequests: data.content,
          totalItemsCount: data.totalElements,
        })
      );
  }

  async getCustomerRequests(pageNumber) {
    this.setState({loading: true})
    await fetch(
      `http://localhost:9090/api/customer-requests?pageNumber=${pageNumber}&pageSize=${this.state.pageSize}`
    )
      .then((response) => response.json())
      .then((data) =>
        this.setState({
          loading:false,
          customerRequests: data.content,
          activePage: pageNumber,
        })
      );
  }

  routeToCustomerChatView(url) {
    this.props.history.push(url);
  }

  async handlePageChange(pageNumber) {
    await this.getCustomerRequests(pageNumber);
  }

  render() {
    const { customerRequests, loading } = this.state;

    
    if(loading){
      return <img src={window.location.origin + '/img/loader.gif'}></img>
    }

    const customerRequestList = customerRequests.map(
      (customerRequest, index) => {
        const addToIndex =
          (this.state.activePage - 1) * this.state.pageSize + 1;
        const message =
          customerRequest.message.length > 50
            ? customerRequest.message.substring(0, 50) + '...'
            : customerRequest.message;
        return (
          <tr key={customerRequest.id}>
            <th scope='row'>{index + addToIndex}</th>
            <td>{message}</td>
            <td>{customerRequest.type}</td>
            <td>{customerRequest.status?.replaceAll('_', ' ')}</td>
            <td>{customerRequest.createdOn}</td>
            <td>
              <div className='d-inline justify-content-center text-center'>
                <a
                  onClick={() =>
                    this.routeToCustomerChatView(
                      '/agent/chat/view/' + customerRequest.id
                    )
                  }
                  className='btn btn-info btn-circle btn-sm'
                  title='View Customer Request'
                >
                  <i className='fas fa-info-circle' />
                </a>
              </div>
            </td>
          </tr>
        );
      }
    );

    return (
      <div>
        <Header></Header>
        <div className='container'>
          <table className='table'>
            <thead>
              <tr>
                <th scope='col'>#</th>
                <th scope='col'>Message</th>
                <th scope='col'>Request Type</th>
                <th scope='col'>Request Status</th>
                <th scope='col'>Request Date</th>
                <th scope='col'>Actions</th>
              </tr>
            </thead>
            <tbody>{customerRequestList}</tbody>
          </table>
          <Pagination
            activePage={this.state.activePage}
            itemsCountPerPage={this.state.pageSize}
            totalItemsCount={this.state.totalItemsCount}
            pageRangeDisplayed={5}
            itemClass='page-item'
            linkClass='page-link'
            onChange={this.handlePageChange.bind(this)}
          />
        </div>
      </div>
    );
  }
}

export default CustomerRequest;
