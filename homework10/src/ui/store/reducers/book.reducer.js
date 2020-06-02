import axios from "axios";
import {FAILURE, REQUEST, SUCCESS} from "../../utils/action-type.util";

export const ACTION_TYPES = {
    FETCH_BOOKS: 'FETCH_BOOKS',
    ADD_BOOK: 'ADD_BOOK',
    DELETE_BOOK: 'DELETE_BOOK',
    UPDATE_BOOK: 'UPDATE_BOOK',
    UPDATE_BOOK_LOCAL: 'UPDATE_BOOK_LOCAL',
};

const initialState = {
    pending: false,
    books: [],
    error: null
}

export const fetchBook = () => ({
    type: ACTION_TYPES.FETCH_BOOKS,
    payload: axios.get('api/book')
});

export const addBook = (book) => ({
    type: ACTION_TYPES.ADD_BOOK,
    payload: axios.post('api/book', book)
});

export const deleteBook = (bookId) => ({
    type: ACTION_TYPES.DELETE_BOOK,
    payload: axios.delete(`api/book/${bookId}`)
});

export const updateBook = (book) => ({
    type: ACTION_TYPES.UPDATE_BOOK,
    payload: axios.patch('api/book', book)
});

export const updateBookLocal = (book) => ({
    type: ACTION_TYPES.UPDATE_BOOK_LOCAL,
    payload: book
});

export default function books(state = initialState, action) {
    switch (action.type) {
        case REQUEST(ACTION_TYPES.FETCH_BOOKS):
        case REQUEST(ACTION_TYPES.DELETE_BOOK):
        case REQUEST(ACTION_TYPES.ADD_BOOK):
            return {
                ...state,
                pending: true,
                error: null
            }
        case SUCCESS(ACTION_TYPES.FETCH_BOOKS):
            return {
                ...state,
                pending: false,
                books: action.payload.data
            }
        case SUCCESS(ACTION_TYPES.DELETE_BOOK):
            return {
                ...state,
                pending: false,
                books: state.books.filter((book) => book.id !== action.payload.data)
            }
        case SUCCESS(ACTION_TYPES.ADD_BOOK):
            return {
                ...state,
                pending: false
            }
        case FAILURE(ACTION_TYPES.FETCH_BOOKS):
        case FAILURE(ACTION_TYPES.DELETE_BOOK):
        case FAILURE(ACTION_TYPES.ADD_BOOK):
            return {
                ...state,
                pending: false,
                error: action.error
            }
        case ACTION_TYPES.UPDATE_BOOK_LOCAL:
            return {
                ...state.books,
                books: state.books.map(book => book.id === action.payload.id ?
                    {...action.payload} :
                    book)
            };
        default:
            return state;
    }
}

export const getBooks = state => state.books.books;
export const getBooksPending = state => state.pending;
export const getBooksError = state => state.error;