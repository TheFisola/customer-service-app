import React, { Component } from 'react';
import './Header.css';
import { Link } from 'react-router-dom';

class Header extends Component {
  constructor(props) {
    super(props);
    this.state = {
      agent: {},
    };
  }

  componentDidMount() {
    const agent = localStorage.getItem('LOGGED_IN_AGENT');
    if (agent === null) {
      this.logout();
      return;
    }
    this.setState({ agent: JSON.parse(agent) });
  }

  logout() {
    localStorage.clear();
    window.location.href = window.location.origin;
  }

  render() {
    const agent = this.state.agent;

    return (
      <nav className='navbar navbar-expand navbar-dark bg-dark topbar mb-4 static-top shadow'>
        <a class='navbar-brand' href='#'>
          CSMA
        </a>
        <button
          id='sidebarToggleTop'
          className='btn btn-link d-md-none rounded-circle mr-3'
        >
          <i className='fa fa-bars' />
        </button>

        <ul className='navbar-nav ml-auto'>
          <li className='nav-item dropdown no-arrow'>
            <a
              className='nav-link dropdown-toggle'
              href='#'
              id='userDropdown'
              role='button'
              data-toggle='dropdown'
              aria-haspopup='true'
              aria-expanded='false'
            >
              <span className='mr-2 d-none d-lg-inline text-white'>
                {agent.name}
              </span>
              <img
                className='img-profile rounded-circle'
                src={window.location.origin + '/img/undraw_profile_2.svg'}
              />
            </a>

            <div
              className='dropdown-menu dropdown-menu-right shadow animated--grow-in'
              aria-labelledby='userDropdown'
            >
              <a
                onClick={() => {
                  this.logout();
                }}
                className='dropdown-item'
              >
                <i className='fas fa-sign-out-alt fa-sm fa-fw mr-2 text-gray-400' />
                Logout
              </a>
            </div>
          </li>
        </ul>
      </nav>
    );
  }
}

export default Header;
