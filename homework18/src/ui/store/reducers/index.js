import {combineReducers} from "redux";
import books from "./book.reducer";
import auth from "./auth.reducer";

const reducer = combineReducers({
    books,
    auth,
});

export default reducer;