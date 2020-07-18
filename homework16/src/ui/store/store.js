import {applyMiddleware, compose, createStore} from 'redux';
import thunk from 'redux-thunk';
import reducer from "./reducers";
import promiseMiddleware from 'redux-promise-middleware';

const composeEnhancers = window['__REDUX_DEVTOOLS_EXTENSION_COMPOSE__'] || compose;


const defaultMiddlewares = [
    thunk,
    promiseMiddleware,
];

const store = createStore(reducer, composeEnhancers(applyMiddleware(...defaultMiddlewares)));

export default store;