import React, { Component } from 'react';
import './Home.css';
import { Link } from 'react-router-dom';

class Home extends Component {
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
                        <Link
                          to='/agent/login'
                          className='btn btn-dark btn-user btn-block'
                        >
                          Login As An Agent
                        </Link>
                        <Link
                          to='/user/login'
                          className='btn btn-dark btn-user btn-block'
                        >
                          Login As A User
                        </Link>
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

export default Home;
