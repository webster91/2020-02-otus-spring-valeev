import React, {Component} from 'react';
import {Navbar, NavbarBrand} from 'react-bootstrap';
import {NavLink} from 'react-router-dom';
import {connect} from "react-redux";
import {fetchUser, getUser, isAuth, logout} from "../../store/reducers/auth.reducer";
import {roles} from "../../config/constants";
import withRouter from "react-router-dom/es/withRouter";
import './navigation-bar.css'
import Button from "react-bootstrap/Button";


class NavigationBar extends Component {

    componentDidMount = () => {
        this.props.fetchUser();
    }

    logout = () => {
        this.props.logout();
    }

    render() {
        const {user, isAuth} = this.props;
        return (
            <React.Fragment>
                {!!isAuth &&
                <Navbar bg="dark" variant="dark" expand="lg" sticky="top">
                    <NavbarBrand to='/'>
                        <img src={"https://upload.wikimedia.org/wikipedia/commons/b/ba/Book_icon_1.png"}
                             width={25}
                             height={25}
                             alt={'brand'}/>
                        Библиотека
                    </NavbarBrand>
                    <NavLink to="/" className="nav-item nav-link">Список книг</NavLink>
                    {!!user.roles && !!user.roles.includes(roles.ADMIN) &&
                    <NavLink to="/add" className="nav-item nav-link">Добавить книгу</NavLink>}
                    <div className={'user-info'}>
                        <div className={'user-name'}>Пользователь: {user.name}</div>
                        <Button className={'logout-link btn-sm'} onClick={() => this.logout()}>Выйти</Button>
                    </div>
                </Navbar>
                }
            </React.Fragment>
        );
    }
}

const mapStateToProps = state => ({
    user: getUser(state),
    isAuth: isAuth(state)
})

const mapDispatchToProps = {fetchUser, logout};

export default withRouter(connect(
    mapStateToProps,
    mapDispatchToProps
)(NavigationBar));