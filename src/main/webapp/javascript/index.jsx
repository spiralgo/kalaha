import React from 'react';
import ReactDOM from 'react-dom';
import App from './components/App';
import {AppContainer} from 'react-hot-loader';
import {HashRouter} from 'react-router-dom';
import {Provider} from 'react-redux'
import {store} from "./store";

const render = (Component) => {
    ReactDOM.render(
        <AppContainer>
            <Provider store={store}>
                <HashRouter>
                    <Component/>
                </HashRouter>
            </Provider>
        </AppContainer>,
        document.getElementById('react-app-root')
    );
};

render(App);
