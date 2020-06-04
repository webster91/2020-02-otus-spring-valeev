import React from 'react';
import {Navbar, NavbarBrand} from 'react-bootstrap';
import {NavLink} from 'react-router-dom';


const NavigationBar = () => {
    return (
        <Navbar bg="dark" variant="dark" expand="lg" sticky="top">
            <NavbarBrand to='/'>
                <img src={"https://upload.wikimedia.org/wikipedia/commons/b/ba/Book_icon_1.png"} width={25} height={25}
                     alt={'brand'}/>
                Библиотека
            </NavbarBrand>
            <NavLink to="/" className="nav-item nav-link">Список книг</NavLink>
            <NavLink to="/add" className="nav-item nav-link">Добавить книгу</NavLink>
        </Navbar>
    );
}

export default NavigationBar;