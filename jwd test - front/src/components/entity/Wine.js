import React from 'react';
import TestAxios from '../../apis/TestAxios';
import { Row, Col, Button, Table, Form } from 'react-bootstrap'
import './../../index.css';
import { withParams, withNavigation } from '../../routeconf'

class Wine extends React.Component {

constructor(props) {
    super(props);

    const search = {
        wineryId: "",
        name: ""
    }

    this.state = {
        wines: [],
        wineries: [],
        number: 0,
        buyGet: 0,
        pageNo: 0,
        totalPages: 0,
        search: search
    }
}

componentDidMount() {
    this.getWines(0);
    this.getWineries();
}

getWines(newPageNo) {
    let config = {
        params: {
            wineryId: this.state.search.wineryId,
            name: this.state.search.name,
            pageNo: newPageNo,
        }
    }

    TestAxios.get('/wines', config)
        .then(res => {
            console.log(res);
            this.setState({
                wines: res.data,
                pageNo: newPageNo,
                totalPages : res.headers['total-pages']
            });
        })
        .catch(error => {
            // handle error
            console.log(error);
            alert('Error occured please try again!');
        });
}

getWineries(){
    TestAxios.get("/wineries")
    .then((response)=>{
        this.setState({wineries:response.data});
    })
    .catch((err=>{console.log(err)}));
}

// getGenresStringFromList(list) {
//     return list.map(element => element.naziv).join(',');
// }


deleteFromState(wineId) {
    var wines = this.state.wines;
    wines.forEach((element, index) => {
        if (element.id === wineId) {
            wines.splice(index, 1);
            this.setState({ wines: wines });
            window.location.reload();
        }
    });
}

delete(wineId) {
    TestAxios.delete('/wines/' + wineId)
        .then(res => {
            // handle success
            console.log(res);
            alert('Wine was deleted successfully!');
            this.deleteFromState(wineId); // ili refresh page-a window.location.reload();
        })
        .catch(error => {
            // handle error
            console.log(error);
            alert('Error occured please try again!');
        });
}

onInputChange(event) {
    const name = event.target.name;
    const value = event.target.value

    let search = this.state.search;
    search[name] = value;

    this.setState({ search })
}

goToAdd() {
    this.props.navigate('/wines/add');
}

onNumberChange(event) {
    console.log(event.target.value);

    const { name, value } = event.target;
    console.log(name + ", " + value);

    this.setState((state, props) => ({
        number: value}));
}

buyThis(wine) {

    var params = {
            'id': wine.id,
            'stock' : parseInt(wine.stock) - parseInt(this.state.number),
            'name' : wine.name,
            'price' : wine.price,
            'typeId' : wine.typeId,
            'year' : wine.year,
            'wineryId' : wine.wineryId,
            'description' : wine.description,
        }


    console.log(params)

    TestAxios.put("/wines/" + wine.id, params)
    .then((res)=>{
        this.getWines(0)
        alert("Uspesno obavljena kupovina")
        this.props.navigate("/wines")
        console.log(res)
    })
    .catch((err=>{
        alert("Nedovoljan broj vina na stanju")
        this.props.navigate("/wines")
        console.log(err)}));

}

addThis(wine) {
    var params = {
        'id': wine.id,
        'stock' : parseInt(this.state.number) + parseInt(wine.stock),
        'name' : wine.name,
        'price' : wine.price,
        'typeId' : wine.typeId,
        'year' : wine.year,
        'wineryId' : wine.wineryId,
        'description' : wine.description,
    }


console.log(params)

    TestAxios.put("/wines/" + wine.id, params)
    .then((res)=>{
        this.getWines()
        this.props.navigate("/wines")
        window.location.reload(true);
    })
    .catch((err=>{
        alert("Neuspesna nabavka")
        this.props.navigate("/wines")
        console.log(err)}));

}


renderWines() {
    return this.state.wines.map((wine) => {

        const admin = window.localStorage['role'] == 'ROLE_ADMIN';

        if(wine.stock > 0 || admin) {
        return (
            <tr key={wine.id}>
                <td>{wine.name}</td>
                <td>{wine.wineryName}</td>
                <td>{wine.typeName}</td>

                {window.localStorage['role'] == 'ROLE_KORISNIK' ?
                    [<td>{wine.description}</td>]:[null]
                }

                <td>{wine.year}</td>
                <td>{wine.price}</td>

                {window.localStorage['role'] == 'ROLE_ADMIN' ?
                    [<td>{wine.stock}</td>]:[null]
                }

                {window.localStorage['role'] == 'ROLE_ADMIN' ?
                    [<td><Button variant="danger" onClick={() => this.delete(wine.id)}>Delete</Button></td>,
                        <td hidden={wine.stock > 10}><Form.Group>
                            <Form.Control
                                name="number"
                                as="input"
                                type="number"
                                placeholder='quantity'
                                min={0}
                                onInput={(e) => this.onNumberChange(e)}></Form.Control>
                        </Form.Group></td>,
                    <td hidden={wine.stock > 11}><Button variant="success" onClick={() => this.addThis(wine)}>Nabavi</Button></td>
                ]:[<td><Form.Group>
                    <Form.Control
                        name="number"
                        as="input"
                        type="number"
                        placeholder='quantity'
                        min={0}
                        onInput={(e) => this.onNumberChange(e)}></Form.Control>
                </Form.Group></td>,
            <td><Button variant="warning" onClick={() => this.buyThis(wine)}>Kupi</Button></td>]}

            </tr>
        )
                } 
    })
}

renderSearchForm() {
    return (
        <>
        <Form style={{ width: "100%" }}>

            <Row><Col>
                <Form.Group>
                    <Form.Label>Vinarija</Form.Label>
                    <Form.Select name="wineryId" onChange={(e)=>this.onInputChange(e)}>
                        <option value=""></option>
                        {this.state.wineries.map((winery)=>{
                            return(
                                <option value={winery.id}>{winery.name}</option>
                            );
                        })}
                    </Form.Select>
                </Form.Group>
            </Col></Row>

            <Row><Col>
                <Form.Group>
                    <Form.Label>Naziv vina</Form.Label>
                    <Form.Control
                        name="name"
                        as="input"
                        type="text"
                        onChange={(e) => this.onInputChange(e)}></Form.Control>
                </Form.Group>
            </Col></Row>

        </Form>
        <Row><Col>
            <Button className="mt-3" onClick={() => this.getWines(0)}>Pretraga</Button>
        </Col></Row>
        </>
    );
}
render() {
    return (
        <Col>
            <Row><h1 style={{color: "purple"}}>Vina</h1></Row>

            <Row>
                {this.renderSearchForm()}
            </Row>
            <br/>

            <Row>

                
                {window.localStorage['role']=='ROLE_ADMIN'?
                <Col>
                <Button onClick={() => this.goToAdd() }>Nabavi vino</Button> 
                </Col> : null}
                 
            
                <Col style={{display:'flex', justifyContent:'right'}}>
                <Button disabled={this.state.pageNo===0} 
                  onClick={()=>this.getWines(this.state.pageNo-1)}
                  className="mr-3">Prev</Button>
                <Button disabled={this.state.pageNo==this.state.totalPages-1} 
                onClick={()=>this.getWines(this.state.pageNo+1)}>Next</Button>
                </Col>
            </Row>

            <Row><Col>
            <Table style={{marginTop: 5}}>
            <thead>
            <tr>
                <th>Naziv</th>
                <th>Vinarija</th>
                <th>Tip</th>

                {window.localStorage['role']=='ROLE_KORISNIK'?
                <th>Opis</th> : null}
                
                <th>Godina proizv.</th>
                <th>Cena</th>

                {window.localStorage['role']==='ROLE_ADMIN'?
                <th>Preostalo</th> : null}
                <th></th>
                <th></th>
            </tr>
            </thead>
            <tbody>
                {this.renderWines(0)}
            </tbody>
            </Table>
            </Col></Row>
            
        </Col>
    );
}
}

export default withNavigation(withParams(Wine));