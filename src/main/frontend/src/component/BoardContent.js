import style from "../css/boardContent.module.css"
import axios from "axios";
import {useParams} from "react-router-dom";
import { useState} from "react";

export default function BoardContent() {
    const boardId = useParams()
    const [title, setTitle] = useState();
    const [category, setCategory] = useState();
    const [content, setContent] = useState();
    const [author, setAuthor]=useState();

    axios.get("/api/board/browsePost", {
        params: {
            id: boardId.id
        }
    })
        .then(res => {
            setTitle(res.data.title)
            setCategory(res.data.category)
            setContent(res.data.content)
            setAuthor(res.data.author)

        })
        .catch()
    const toModify = () => { // 수정 페이지로 이동
        window.location.href = "/write/"+boardId.id
    }


    return (
        <div className={style.container}>
            <div className={style.main}>
                <h1>글쓰기</h1>
                <div className={style.title_header}>
                    <div className={style.title}>
                        <p>제목 :</p>
                        <div>{title}</div>
                    </div>
                    <div>{category}</div>
                </div>


                <div className={style.contents}>
                    <div>{content}</div>
                </div>
                {author===window.sessionStorage.getItem("ID") ? <button onClick={toModify} className={style.toWriteBtn} type="button">수정하기</button>:""}
            </div>
        </div>
    )
}