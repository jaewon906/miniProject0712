import style from "../css/boardWrite.module.css"
import axios from "axios"
import {useEffect, useRef, useState} from "react";
import {useParams} from "react-router-dom";

export default function BoardWrite() {
    const ref1 = useRef(),
        ref2 = useRef(),
        ref3 = useRef();
    const categories = ["자유게시판", "공략게시판", "공부게시판", "꿀팁게시판"];
    let i = 0;
    const [category, setCategory1] = useState("자유게시판");
    const boardNum = useParams();
    const [data, setData] = useState();

    useEffect(()=>{
        axios.get("/api/board/write",{
            params:
                {
                    id:boardNum.id
                }
        })
            .then(res=>setData(res.data))
            .catch()
    },[setData])


    const toSubmit = () => { // 글 저장을 위한 함수
        const title = ref1.current.value,
            id = ref2.current.value,
            content = ref3.current.value,
            author = window.sessionStorage.getItem("ID"),
            ret = window.confirm("등록하시겠습니까?")

        if (ret) {
            if (title.length === 0) {
                alert("제목을 입력해주세요")
            } else if (content.length === 0) {
                alert("내용을 입력해주세요")
            } else {
                axios.post("/api/board/write", null, {
                    params: { //data속성에 json데이터를 넣어놓으면 안된다. 데이터가 "data"라는 dictionary로 가게되서 바로 꺼낼수가없음.
                        // config속성에 넣어놓자.
                        title: title,
                        category: category,
                        content: content,
                        author: author,
                        id:id
                    }
                })
                    .then(res => console.log(res.data)) // res.data는 스프링에서 @PostMapping에 해당하는 메서드의 리턴값
                    .catch(err => console.error(err))
                alert("등록되었습니다.")
                window.location.href = "/board";
            }
        }
    }

    const selectCategory = (val) => {
        setCategory1(val);
    }

    return (
        <>{data!==undefined?
        <div className={style.container}>
            <div className={style.main}>
                <h1>글쓰기</h1>
                <div className={style.title_header}>
                    <input type="hidden" name="id" ref={ref2} value={data.id}/>
                    <div className={style.title}>
                        <p>제목 :</p> <textarea placeholder="제목을 입력해주세요" ref={ref1}>{data.title}</textarea>
                    </div>
                    <select onChange={(e) => selectCategory(e.target.value)}>
                        {categories.map(el => {
                            return (
                                <option key={el} value={el}>{el}</option>
                            )

                        })}
                    </select>
                </div>
                <div className={style.contents}>
                    <textarea placeholder="내용을 입력해주세요" ref={ref3}>{data.content}</textarea>
                </div>
                <button onClick={toSubmit} className={style.toWriteBtn} type="button">등록하기</button>
            </div>
        </div>:""}</>
    )
}