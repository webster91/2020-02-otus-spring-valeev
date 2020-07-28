import React, {Component} from "react";
import Form from "react-bootstrap/Form";
import './book-form.css';
import Button from "react-bootstrap/Button";
import {addBook} from "../../store/reducers/book.reducer";
import {connect} from "react-redux";
import {withRouter} from "react-router-dom";

class BookForm extends Component {

    handleSubmit = () => {
        const {bookName, authorName, genreName} = this.state;
        const book = {
            name: bookName,
            author: {
                name: authorName,
            },
            genre: {
                name: genreName,
            },
        }
        this.props.addBook(book);
        this.props.history.push('/book');
    }

    handleBookNameChange = (event) => {
        this.setState({
            bookName: event.target.value
        });
    };

    handleAuthorNameChange = (event) => {
        this.setState({
            authorName: event.target.value
        });
    };

    handleGenreNameChange = (event) => {
        this.setState({
            genreName: event.target.value
        });
    };

    render() {
        return (
            <Form className={'formContainer'}>
                <Form.Group controlId="holderName">
                    <Form.Label>Название</Form.Label>
                    <Form.Control type="text" placeholder="Название" onChange={this.handleBookNameChange}/>
                </Form.Group>

                <Form.Group controlId="holderAuthor">
                    <Form.Label>Автор</Form.Label>
                    <Form.Control type="text" placeholder="Автор" onChange={this.handleAuthorNameChange}/>
                </Form.Group>

                <Form.Group controlId="holderGenre">
                    <Form.Label>Жанр</Form.Label>
                    <Form.Control type="text" placeholder="Жанр" onChange={this.handleGenreNameChange}/>
                </Form.Group>

                <Button variant="primary" onClick={this.handleSubmit}>
                    Отправить
                </Button>
            </Form>
        )
    }

}

const mapStateToProps = null;
const mapDispatchToProps = {addBook};

export default withRouter(connect(
    mapStateToProps,
    mapDispatchToProps
)(BookForm));