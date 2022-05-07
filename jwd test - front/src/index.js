import React from 'react';
import ReactDOM from 'react-dom';
import { Route, Link, HashRouter as Router, Routes, Navigate } from 'react-router-dom';
import { Navbar, Nav, Button, Container} from 'react-bootstrap';
import Home from './components/Home';
import Login from './components/authorization/Login'
import Wine from './components/entity/Wine';
import NotFound from './components/NotFound';
import {logout} from './services/auth';
import Add from './components/entity/Add';


class App extends React.Component {

    constructor(props){
        super(props);

        this.state={
            entityEdit : {}
        }
    }

    editEntitySelected(entity){
        this.setState({entityEdit:entity});
    }

    render() {
        const admin = window.localStorage['role']=='ROLE_ADMIN';
        const user = window.localStorage['role']=='ROLE_KORISNIK';

        if(admin){
            return (
            <>
                <Router>
                    <Navbar expand bg="dark" variant="dark">
                        <Navbar.Brand as={Link} to="/">
                            Winery JWD Test
                        </Navbar.Brand>

                        <Nav>
                        <Nav.Link as={Link} to="/wines " > 
                            Wines
                        </Nav.Link>

                        <Nav.Link as={Link} to="/wines/add"> 
                            Add Wine
                        </Nav.Link>

                        <Button onClick={()=>logout()}>Logout</Button>:
                        </Nav>
                    </Navbar>
                    <Container style={{paddingTop:"10px"}}>
                    <Routes>
                        <Route path="/" element={<Home/>} />
                        <Route path="/login" element={<Navigate replace to='/'/>} />
                        <Route path="/wines" element={<Wine />} />
                        <Route path="/wines/add" element={<Add/>} /> 
                        <Route path="*" element={<NotFound/>} />
                    </Routes>
                </Container>
                </Router>
            </>
        );
        }else if (user) {
            return (
                <>
                    <Router>
                        <Navbar expand bg="dark" variant="dark">
                            <Navbar.Brand as={Link} to="/">
                                Winery JWD Test
                            </Navbar.Brand>
    
                            <Nav>
                            <Nav.Link as={Link} to="/wines " > 
                                Wines
                            </Nav.Link>
    
                            <Button onClick={()=>logout()}>Logout</Button>:
                            </Nav>
                        </Navbar>
                        <Container style={{paddingTop:"10px"}}>
                        <Routes>
                            <Route path="/" element={<Home/>} />
                            <Route path="/login" element={<Navigate replace to='/'/>} />
                            <Route path="/wines" element={<Wine />} />
                            <Route path="*" element={<NotFound/>} />
                        </Routes>
                    </Container>
                    </Router>
                </>
            );
        } else {
            return( 
                <>
                    <Router>
                    <Navbar expand bg="dark" variant="dark">
                            <Navbar.Brand as={Link} to="/">
                                Winery JWD Test
                            </Navbar.Brand>
                            <Nav>
                            <Nav.Link as={Link} to="/login">
                                Login
                            </Nav.Link>
                            </Nav>
                        </Navbar>
                        <Container style={{paddingTop:"10px"}}>
                        <Routes>
                            <Route path="/" element={<Home/>} />
                            <Route path="/login" element={<Login/>}/>
                            <Route path="*" element={<Navigate replace to = "/login"/>}/>
                        </Routes>
                        </Container>
                    </Router>
                </>);
        }
    }
};


ReactDOM.render(
    <App/>,
    document.querySelector('#root')
);
