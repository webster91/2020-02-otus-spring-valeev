import React, {Component} from 'react';
import BootstrapTable from 'react-bootstrap-table-next';
import './book-list.css';
import {connect} from "react-redux";
import {
    deleteBook,
    fetchBook,
    getBooks,
    getBooksError,
    getBooksPending,
    updateBook,
    updateBookLocal
} from "../../store/reducers/book.reducer";
import Button from "react-bootstrap/Button";
import Container from "react-bootstrap/Container";
import Col from "react-bootstrap/Col";
import Row from "react-bootstrap/Row";
import cellEditFactory from 'react-bootstrap-table2-editor';

class BookList extends Component {

    initialState = {
        editableRow: undefined,
        oldRow: undefined
    };

    state = {...this.initialState};

    componentDidMount = () => {
        this.props.fetchBook();
    }

    buttonDelete = (cellContent, row) => {
        return <Button className="btn btn-danger btn-xs"
                       onClick={() => this.props.deleteBook(row.id)}>X</Button>
    }

    buttonEdit = (cell, row, rowIndex, editableRow) => {
        if (editableRow === row.id) {
            return (
                <Row>
                    <Col lg={6}>
                        <Button onClick={() => this.handleSave(row)} variant="success">
                            Ок
                        </Button>
                    </Col>
                    <Col lg={6}>
                        <Button onClick={() => this.handleCancel(row)} variant="danger">
                            X
                        </Button>
                    </Col>
                </Row>
            );
        } else {
            return (
                <Container fluid>
                    <Button onClick={() => this.handleEdit(row)} variant="outline-primary">
                        Изменить
                    </Button>
                </Container>
            );
        }
    };

    handleEdit = (row) => {
        this.setState({
            editableRow: row.id,
            oldRow: JSON.parse(JSON.stringify(row)),
        });
    };

    getNonEditableRows = () => {
        const books = this.props.books || [];
        return !!books && books.map((book) => {
            return book.id;
        }).filter((id) => {
            return id !== this.state.editableRow;
        });
    };

    handleSave = (row) => {
        this.setState({
            editableRow: undefined
        });
        const rq = {
            id: row.id,
            name: row.name,
            author: row.author,
            genre: row.genre
        }
        this.props.updateBook(rq);
    };

    handleCancel = () => {
        this.setState({
            editableRow: undefined
        });
        this.props.updateBookLocal(this.state.oldRow);
    };

    render() {
        const columns = [{
            dataField: 'id',
            text: 'Ид',
            sort: true,
            headerStyle: () => {
                return {width: "30%"};
            }
        }, {
            dataField: 'name',
            text: 'Имя',
            sort: true
        }, {
            dataField: 'author.name',
            text: 'Автор',
            sort: true
        }, {
            dataField: 'genre.name',
            text: 'Жанр',
            sort: true
        },
            {
                text: "Удалить",
                formatter: this.buttonDelete,
                headerStyle: {width: "90px"},
                style: {textAlign: 'center'},
                editable: false,
            },
            {
                text: "Изменить",
                formatter: this.buttonEdit,
                formatExtraData: this.state.editableRow,
                headerStyle: {width: "150px"},
                style: {textAlign: 'center'},
                editable: false,
            },
        ];

        const books = this.props.books;
        return (
            <div className={'bookContainer'}>
                <BootstrapTable
                    data={books}
                    columns={columns}
                    cellEdit={cellEditFactory({
                        mode: 'click',
                        nonEditableRows: () => this.getNonEditableRows(),
                        blurToSave: true
                    })}
                    keyField='id'
                    striped
                    bootstrap4
                />
            </div>
        )
    }
}

const mapStateToProps = state => ({
    books: getBooks(state),
    pending: getBooksPending(state),
    error: getBooksError(state)
})

const mapDispatchToProps = {fetchBook, deleteBook, updateBookLocal, updateBook};

export default connect(
    mapStateToProps,
    mapDispatchToProps
)(BookList);