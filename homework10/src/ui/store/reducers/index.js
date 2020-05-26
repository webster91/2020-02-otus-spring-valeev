import {combineReducers} from "redux";
import books from "./book.reducer";

const reducer = combineReducers({
    books,
});

export default reducer;