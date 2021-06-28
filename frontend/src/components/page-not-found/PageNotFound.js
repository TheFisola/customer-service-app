import React, { Component } from 'react';
import './PageNotFound.css';

class PageNotFound extends Component {
  render() {
    return (
      <div className='container-fluid top'>
        <div className='text-center'>
          <div className='error mx-auto text-lg' data-text={404}>
            404
          </div>
          <p className='lead text-gray-800 mb-5'>Page Not Found</p>
      
        </div>
      </div>
    );
  }
}

export default PageNotFound;
