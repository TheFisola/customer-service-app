import React, { Component } from 'react';
import './Login.css';
import { Link } from 'react-router-dom';

class Login extends Component {
  render() {
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
                        <div className='form-group'>
                          <input
                            type='email'
                            className='form-control form-control-user'
                            id='exampleInputEmail'
                            aria-describedby='emailHelp'
                            placeholder='Enter Email Address...'
                          />
                        </div>
                        <Link
                        to='/agent/customer/requests'
                         
                          className='btn btn-dark btn-user btn-block'
                        >
                          Login
                        </Link>
                        <hr />
                        <div className='text-center text-dark'>
                          <Link to='/agent/login' className='small text-dark'>
                            Login as an Admin
                          </Link>
                          {/* <Link to='/user/login' className='small text-dark'>
                            Login as a User
                          </Link> */}
                        </div>
                      </form>

                      <hr />
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
