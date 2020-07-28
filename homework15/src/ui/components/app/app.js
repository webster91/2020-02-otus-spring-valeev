import React from "react";
import {BrowserRouter as Router, Route, Switch,} from "react-router-dom";
import {AddBookPage, BooksPage, ErrorPage} from "../pages";
import NavigationBar from "../navigation/navigation-bar";

const App = () => {
    return (
        <Router>
            <NavigationBar/>
            <Switch>
                <Route exact path={["/", "/book"]} component={BooksPage}/>
                <Route path="/add" component={AddBookPage}/>
                <Route path={["error", "/**"]} component={ErrorPage}/>
            </Switch>
        </Router>
    );
}

export default App