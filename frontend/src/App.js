import React, { Component } from 'react';
import 'bootstrap/dist/css/bootstrap.min.css'
import 'bootstrap/dist/js/bootstrap.bundle'
import '@fortawesome/fontawesome-free/css/all.min.css'
import './App.css';
import { BrowserRouter as Router, Route, Switch } from 'react-router-dom';
import Login from './components/login/Login';
import CustomerRequest from './components/customer-request/CustomerRequest';
import CustomerChatUI from './components/customer-chat-ui/CustomerChatUI';
import Home from './components/home/Home';
import PageNotFound from './components/page-not-found/PageNotFound';

class App extends Component {
  render() {
    return (
      <Router>
        <Switch>
          <Route path='/' exact={true} component={Home} />
          <Route path='/user/login' exact={true} component={Login} />
          <Route path='/agent/login' exact={true} component={Login} />
          <Route
            path='/agent/customer/requests'
            exact={true}
            component={CustomerRequest}
          />
          <Route path='/agent/chat/:id' component={CustomerChatUI} />
          <Route path='/user/chat/:id' component={CustomerChatUI} />
          <Route component={PageNotFound} />
        </Switch>
      </Router>
    );
  }
}

export default App;
