import {Outlet, Route, Routes} from 'react-router-dom'
import Main from "./component/main";
import BoardMain from "./component/BoardMain";
import BoardWrite from "./component/BoardWrite";
import SignUp from "./component/SignUp";
import LogIn from "./component/LogIn";
import Withdrawal from "./component/Withdrawal";
import BoardContent from "./component/BoardContent";
function App() {
  return (
   <Routes>
       <Route path="/" element={<Main/>}/>
       <Route path="/" element={<Outlet/>}>
           <Route path="/signUp" element={<SignUp/>}/>
           <Route path="/logIn" element={<LogIn/>}/>
           <Route path="/withdrawal" element={<Withdrawal/>}/>
           <Route path="write/:id" element={<BoardWrite/>}/>
           <Route path="board/" element={<BoardMain/>}/>
           <Route path="board/" element={<Outlet/>}>
               <Route path="/board/category/:id" element={<BoardMain/>}/>
               <Route path="/board/:id" element={<BoardContent/>}/>
           </Route>
       </Route>
   </Routes>
  );
}

export default App;
