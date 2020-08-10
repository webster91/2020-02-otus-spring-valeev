import React from 'react'
import ReactDOM from 'react-dom'
import {Provider} from "react-redux";
import App from "./components/app";
import store from './store/store';
import setupAxiosInterceptors from "./config/axios-interceptor";

import 'bootstrap/dist/js/bootstrap.bundle.min';
import 'bootstrap/dist/css/bootstrap.min.css';
import 'react-bootstrap-table-next/dist/react-bootstrap-table2.min.css';
import ErrorBoundary from "./components/error-boundry";

setupAxiosInterceptors();
const rootEl = document.getElementById('root');

const render = Component =>
    ReactDOM.render(
        <Provider store={store}>
            <ErrorBoundary>
                <Component/>
            </ErrorBoundary>
        </Provider>,
        rootEl
    )

render(App);
