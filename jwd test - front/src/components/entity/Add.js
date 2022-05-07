import React from "react";
import { Form, Button, Row, Col, InputGroup } from "react-bootstrap";
import { withNavigation } from "../../routeconf";
import TestAxios from "../../apis/TestAxios";

class Add extends React.Component {
  constructor(props) {
    super(props);

    let wines = {
      name : "",
      description: "",
      year: "",
      price: "",
      typeId: "",
      wineryId: "",
    };

    this.state = { wines : wines, types : [], wineries: []};
    this.create = this.create.bind(this);

  }

  componentDidMount() {
    this.getTypes();
    this.getWineries();
    }

    async getTypes(){
        TestAxios.get("/types")
        .then((response)=>{
            this.setState({types:response.data});
        })
        .catch((err=>{console.log(err)}));
    }

    async getWineries(){
      TestAxios.get("/wineries")
      .then((response)=>{
          this.setState({wineries:response.data});
      })
      .catch((err=>{console.log(err)}));
  }

    async create() {

    let params = {
      "name" : this.state.wines.name,
      "price": this.state.wines.price,
      "year": this.state.wines.year,
      "price" : this.state.wines.price,
      "description" : this.state.wines.description,
      "typeId" : this.state.wines.typeId,
      "wineryId" : this.state.wines.wineryId
    };

    await TestAxios.post("/wines", params)
      .then((res) => {
        // handle success
        console.log(res);

        alert("Wine was added successfully!");
        this.props.navigate("/wines");
      })
      .catch((error) => {
        // handle error
        console.log(error);
        alert("Error occured please try again!");
      });
  }

  onInputChange(event) {
    const name = event.target.name;
    const value = event.target.value;

    console.log(value)

    let wines = this.state.wines;
    wines[name] = value;

    this.setState({ wines })
}


  render() {
    return (
      <>
        <Row>
          <Col></Col>
          <Col xs="12" sm="10" md="8">
            <h1 style={{color: "purple"}}>Dodavanje vina</h1>
            <Form>

              <Form.Label htmlFor="name">Naziv vina</Form.Label>
              <Form.Control
                placeholder="Naziv vina"
                name="name"
                type="text"
                onChange={(e) => this.onInputChange(e)}
              />

              <Form.Label htmlFor="description">Opis</Form.Label>
              <Form.Control
                placeholder="Opis vina"
                name="description"
                type="text"
                onChange={(e) => this.onInputChange(e)}
              />

              <Form.Label htmlFor="year">Godina proizvodnje</Form.Label>
              <Form.Control
                placeholder="Godina proizvodnje"
                name="year"
                min={1900}
                type="number"
                onChange={(e) => this.onInputChange(e)}
              />

            <Form.Label htmlFor="price">Cena po flasi</Form.Label>
              <Form.Control
                placeholder="Cena po flasi"
                name="price"
                min={0}
                type="number"
                onChange={(e) => this.onInputChange(e)}
              />

              <Form.Group>
                    <Form.Label>Tip vina</Form.Label>
                    <Form.Select name="typeId" onChange={(e)=>this.onInputChange(e)}>
                        <option value=""></option>
                        {this.state.types.map((type)=>{
                            return(
                                <option value={type.id}>{type.name}</option>
                            );
                        })}
                    </Form.Select>
                </Form.Group>

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

              <Button style={{ marginTop: "25px" }} onClick={this.create}>
                Kreiraj vino
              </Button>
            </Form>
          </Col>
          <Col></Col>
        </Row>
      </>
    );
  }
}

export default withNavigation(Add);
