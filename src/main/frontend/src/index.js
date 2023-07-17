import React from 'react';
import ReactDOM from 'react-dom/client';
import App from './App';
import {BrowserRouter} from 'react-router-dom'
import ScrollTop from "./js/ScrollTop";
import Header from "./component/Header";

const root = ReactDOM.createRoot(document.getElementById('root'));
root.render(
    <BrowserRouter>
        <Header/>
        <App/>
        <ScrollTop/>
    </BrowserRouter>
);

