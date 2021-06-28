import React from 'react';
import ReactDOM from 'react-dom';
import App from './components/App';
import { AppContainer } from 'react-hot-loader';
import {BrowserRouter} from 'react-router-dom';
import {Provider} from 'react-redux'
import {store} from "./store";

const render = (Component) => {
  ReactDOM.render(
    <AppContainer>
      <Provider store={store}>
      <BrowserRouter>
        <Component/>
      </BrowserRouter>
      </Provider>
    </AppContainer>,
    document.getElementById('react-app-root')
  );
};

render(App);
