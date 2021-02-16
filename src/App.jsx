import React, { useState } from "react";
import axios from "axios";
import logo from "./logo.svg";
import "./App.css";

function App() {
  const [got, setGot] = useState("nothing");

  axios
    .get("http://localhost:36549/ping")
    .then((res) => {
      setGot(res.data);
    })
    .catch((err) => {
      console.log(err);
    });

  return (
    <div className="App">
      <header className="App-header">
        <h1>Qinpel</h1>
        <h2>Quick Interface for Pointel</h2>
        <h3>{got}</h3>
        <img src={logo} className="App-logo" alt="logo" />
        <p>
          Edit <code>src/App.js</code> and save to reload.
        </p>
        <a
          className="App-link"
          href="https://reactjs.org"
          target="_blank"
          rel="noopener noreferrer"
        >
          Learn React
        </a>
      </header>
    </div>
  );
}

export default App;
