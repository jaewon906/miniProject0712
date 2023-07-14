import {Outlet, Route, Routes} from 'react-router-dom'
import Main from "./component/main";
import BoardMain from "./component/BoardMain";
import BoardWrite from "./component/BoardWrite";
import Login from "./component/Login";
function App() {
  return (
   <Routes>
       <Route path="/" element={<Main/>}/>
       <Route path="/" element={<Outlet/>}>
           <Route path="/login" element={<Login/>}/>
           <Route path="write/" element={<BoardWrite/>}/>
           <Route path="board/" element={<BoardMain/>}/>
           <Route path="board/" element={<Outlet/>}>
               <Route path="/board/category/:id" element={<BoardMain/>}/>
           </Route>
       </Route>
   </Routes>
  );
}

export default App;
