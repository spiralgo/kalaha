import React from 'react';
import ReactDOM from 'react-dom';
import App from './components/App';
import { AppContainer } from 'react-hot-loader';
import {BrowserRouter} from 'react-router-dom';
import rootReducer from './reducers/index'
import {Provider} from 'react-redux'
import {createStore} from 'redux'

const store = createStore(rootReducer)

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

if (module.hot) {
    module.hot.accept('./components/App', () => {
        render(App)
    })
}