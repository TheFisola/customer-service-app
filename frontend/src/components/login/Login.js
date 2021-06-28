import React, { Component } from 'react';
import './Login.css';
import { Link } from 'react-router-dom';

class Login extends Component {
  constructor(props) {
    super(props);
    this.state = {
      loading: false,
    };
  }

  componentDidMount() {
    const agent = localStorage.getItem('LOGGED_IN_AGENT');
    if (agent !== null) {
      this.route(
        '/agent/customer/requests'
      );
    }
  }

  async fakeLogin() {
    try {
      this.setState({ loading: true });
      const email = document.getElementById('email').value;
      if (!email) {
        window.alert('Please provide email!');
        return;
      }
      const response = await fetch(`${process.env.REACT_APP_BASE_URL}/api/login`, {
        method: 'POST',
        body: JSON.stringify({
          email: email,
          role: 'AGENT',
        }),
        headers: {
          Accept: 'application/json',
          'Content-Type': 'application/json',
        },
      });

      if (response.status === 200) {
        const data = await response.json();
        localStorage.setItem('LOGGED_IN_AGENT', JSON.stringify(data));
        this.route(
          '/agent/customer/requests'
        );
      } else {
        const error = document.getElementById('error');
        error.classList.add('error');
      }
    } catch (e) {
      console.log('Error logging in ', e);
      const error = document.getElementById('error');
      error.classList.add('error');
    }
    this.setState({ loading: false });
  }

  route(url) {
    this.props.history.push(url);
  }

  render() {
    const loading = this.state.loading;
    return (
      <div className='container login'>
        <div className='row justify-content-center'>
          <div className='col-xl-10 col-lg-12 col-md-9'>
            <div className='card o-hidden border-0 shadow-lg my-5'>
              <div className='card-body p-0'>
                <div className='row'>
                  <div className='col-lg-6 d-none d-lg-block bg-login-image' />
                  <div className='col-lg-6'>
                    <div className='p-5'>
                      <div className='text-center'>
                        <h1 className='h4 text-gray-900 mb-4'>Welcome Back!</h1>
                      </div>
                      <form className='user'>
                        <div className='form-group text-center'>
                          <div id='error' className='d-none'>
                            Invalid Credentials!
                            <br></br>
                          </div>
                          <input
                            type='email'
                            className='form-control form-control-user'
                            id='email'
                            aria-describedby='emailHelp'
                            placeholder='Enter Email Address...'
                          />
                        </div>
                        <a
                          onClick={() => this.fakeLogin()}
                          className='btn btn-dark btn-user btn-block'
                        >
                          Login{' '}
                          {loading && (
                            <span>
                              {' '}
                              <i className='fa fa-spinner' />
                            </span>
                          )}
                        </a>
                        <hr />
                      </form>
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    );
  }
}

export default Login;
