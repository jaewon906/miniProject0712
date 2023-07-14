import {Outlet, Route, Routes} from 'react-router-dom'
import Main from "./component/main";
import BoardMain from "./component/BoardMain";
import BoardCategory from "./component/BoardCategory";
import BoardWrite from "./component/BoardWrite";
function App() {
  return (
   <Routes>
       <Route path="/" element={<Main/>}/>
       <Route path="/" element={<Outlet/>}>
           <Route path="board/" element={<BoardMain/>}/>
           <Route path="board/" element={<Outlet/>}>
               <Route path="category=/:id/" element={<BoardMain/>}/>
               <Route path="write/" element={<BoardWrite/>}/>
           </Route>
       </Route>
   </Routes>
  );
}

export default App;
