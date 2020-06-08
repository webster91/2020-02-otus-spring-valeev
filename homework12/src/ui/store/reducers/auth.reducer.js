import axios from "axios";
import {FAILURE, REQUEST, SUCCESS} from "../../utils/action-type.util";

export const ACTION_TYPES = {
    FETCH_USER: 'FETCH_USER',
    LOGOUT: 'LOGOUT',
}

const initialState = {
    user: {
        id: null,
        name: null,
        login: null,
        roles: [],
    },
    isAuth: false
}

export const fetchUser = () => async (dispatch, getState) => {
    await dispatch({
        type: ACTION_TYPES.FETCH_USER,
        payload: axios.get('api/auth')
    });
}

export const logout = () => async dispatch => {
    await dispatch({
        type: ACTION_TYPES.LOGOUT,
        payload: axios.post('api/logOut')
    });
    window.location.href = 'logout';
}

export default function user(state = initialState, action) {
    switch (action.type) {
        case REQUEST(ACTION_TYPES.FETCH_USER):
        case REQUEST(ACTION_TYPES.LOGOUT):
            return {
                ...state,
                pending: true,
                error: null
            }
        case SUCCESS(ACTION_TYPES.FETCH_USER):
            return {
                ...state,
                user: action.payload.data,
                isAuth: true,
                pending: true
            }
        case SUCCESS(ACTION_TYPES.LOGOUT):
            return {
                ...state,
                user: {
                    id: null,
                    name: null,
                    login: null,
                    roles: [],
                },
                isAuth: false,
                pending: false
            }
        case FAILURE(ACTION_TYPES.FETCH_USER):
        case FAILURE(ACTION_TYPES.LOGOUT):
            return {
                ...state,
                isAuth: false
            }
        default:
            return state;
    }
}

export const getUser = state => state.auth.user;
export const isAuth = state => state.auth.isAuth;