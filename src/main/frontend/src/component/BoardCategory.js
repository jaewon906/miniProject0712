import style from "../css/boardCategory.css"
import {useParams} from "react-router-dom";
export default function BoardCategory(){
    const {categoryId} = useParams();
    let title;
    const categoryIdIdentifier =() =>{
        switch(categoryId){
            case 1: title = "자유게시판";break;
            case 2: title = "공략게시판";break;
            case 3: title = "꿀팁게시판";break;
            case 4: title = "공부게시판";break;
            default: title="여기는 해당하지 않습니다."
        }
    }

    return(
        <form action={"/board/category=" + categoryId} method="post">
        <div className={style.container}>
            <h1>{title}</h1>
            <div className={style.main_header}>
                <div>글 개수 : </div>
                <select>
                    <option value="10개"></option>
                    <option value="50개"></option>
                    <option value="100개"></option>
                </select>

            </div>
            <div className={style.main}>

            </div>
        </div>
        </form>
    )
}