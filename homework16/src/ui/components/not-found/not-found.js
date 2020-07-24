import React from 'react';
import './not-found.css';
import {Link} from "react-router-dom";


const NotFound = () => {
    return (
        <div className="notFoundContainer">
            <h1>Упппсссс! Что то пошло не так</h1>
            <div className="error-details">
                Попробуйте перезагрузить страницу или вернуться на главную!
            </div>
            <Link to="/" className="nav-item nav-link">Главная страница</Link>
        </div>
    );
}

export default NotFound;