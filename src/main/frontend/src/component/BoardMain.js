import style from "../css/boardMain.module.css"
import {Link, useParams} from "react-router-dom";
import axios from "axios";
import {useEffect, useRef, useState} from "react";

let page = 0
export default function BoardMain() {
    const urlParams = useParams();

    const selectList = [10, 30, 50, 100]
    const [getBoard, setGetBoard] = useState([]);
    const [categoryTemp, setCategoryTemp] = useState("");
    const searchBoard = useRef();
    const [searchResult, setSearchResult] = useState([]);
    const [searchBoolean, setSearchBoolean] = useState(true)
    const [pageAttributes, setPageAttributes] = useState()
    const [pages, setPages] = useState(0)
    const [size, setSize] = useState(10)

    const urlQuery = `page=${pages}&size=${size}&sort=id,DESC`

    let sortedList = [{}];
    let i = 0;
    let j = 0;

    useEffect(() => {

        axios.get("/api/board?" + urlQuery)
            .then(res => {
                setPageAttributes(res.data)
                setGetBoard(res.data.content)

            })
            .catch(err => console.error(err))

        switch (urlParams.id) {
            case "1":
                setCategoryTemp("자유게시판");
                break;
            case "2":
                setCategoryTemp("공략게시판");
                break;
            case "3":
                setCategoryTemp("공부게시판");
                break;
            case "4":
                setCategoryTemp("꿀팁게시판");
                break;
            default:
                setCategoryTemp("전체게시판")
        }

    }, [urlParams.id, pages, size, urlQuery]) //url의 id부분과 게시판 개수 변경 버튼을 누를때 마다 렌더링 실행

    const boardAmount = (e) => {       // 게시글 개수 조절

        setPages(0)
        setSize(e.target.value)

    }

    const toSearchBoard = () => {           //게시글 검색
        setPages(0);

        if (searchBoard.current.value !== null) { //여기의 useEffect 안에 넣으면 urlParam변화때만 동작하기 때문에 넣지말자
            setSearchBoolean(false);
        }
        if (searchBoard.current.value === "") {
            setSearchBoolean(true)
        }

        axios.get("/api/board/searchResult?" + urlQuery, {
            params: {
                title: searchBoard.current.value,
            }
        })
            .then(res => setSearchResult(res.data))
            .catch()

        i = 0;
        j = 0;

    }

    const paging = (e) => {          // 페이징 처리
        const movePage = e.target.id
        switch (movePage) {
            case "first":
                page = 0;
                setPages(page)
                break;
            case "last":
                page = pageAttributes.totalPages - 1;
                setPages(page)
                break;
            case "prev":
                --page;
                setPages(page)
                break;
            case "next":
                ++page;
                setPages(page)
                break;
            default :console.error("Warning. you clicked page button, but no change")
        }

    }

    const pageNumOnClick = (e)=>{
        console.log(e.target.value)
        page=parseInt(e.target.id)
        setPages(page)
    }

    return (
        <div className={style.container}>
            <div className={style.main}>
                <h1>{categoryTemp}</h1>
                <div className={style.category}>
                    <Link to="/board/">
                        <div>전체게시판</div>
                    </Link>
                    <Link to="/board/category/1">
                        <div>자유게시판</div>
                    </Link>
                    <Link to="/board/category/2">
                        <div>공략게시판</div>
                    </Link>
                    <Link to="/board/category/3">
                        <div>공부게시판</div>
                    </Link>
                    <Link to="/board/category/4">
                        <div>꿀팁게시판</div>
                    </Link>
                </div>
                <div className={style.boardListAll_header}>
                    <input type="search" onInput={toSearchBoard}
                           ref={searchBoard} className={style.find} placeholder={"제목, 내용, 작성자 검색 가능"}/>

                    <div style={{marginLeft: "50px", marginRight: "10px"}}>글 개수 :</div>
                    <select onChange={boardAmount}>
                        {selectList.map(el => {
                            return (
                                <option key={el} value={el}>{el}개</option>
                            )
                        })}
                    </select>
                </div>
                <div className={style.no_title_author_time}>
                    <div>번호</div>
                    <div>제목</div>
                    <div>작성자</div>
                    <div>작성시간</div>
                    <div>조회수</div>
                    <div key="1">좋아요</div>
                </div>

                <div className={style.boardListAll}>

                    {searchBoolean ? getBoard.map(el => {

                        if (categoryTemp === "전체게시판") {
                            sortedList[i++] = el;
                        }
                        if (categoryTemp === el.category) {
                            sortedList[j++] = el;
                        }
                        return null;

                    }) : searchResult.map(el => {

                        if (categoryTemp === "전체게시판") {
                            sortedList[i++] = el;
                        }
                        if (categoryTemp === el.category) {
                            sortedList[j++] = el;
                        }
                        return null;
                    })}
                    {sortedList.map(el => {
                        let yyyy = "";
                        let MM = "";
                        let dd = "";
                        try {
                            yyyy = el.boardCreatedTime.substring(0, 5);
                            MM = el.boardCreatedTime.substring(5, 8);
                            dd = el.boardCreatedTime.substring(8, 10);
                        } catch (e) {
                        }

                        return (
                            <div className={style.list} key={el.id + ""}>
                                <div style={{width: "4%"}}>{el.id}</div>
                                <div style={{width: "45%"}}><Link to={"/board/" + el.id}>{el.title}</Link></div>
                                <div style={{width: "20%"}}>{el.author}</div>
                                <div style={{width: "11%"}}>{yyyy + MM + dd}</div>
                                <div style={{width: "10%"}}>{el.hit}</div>
                                <div style={{width: "10%"}}>{el.boardLikes}</div>
                            </div>
                        )
                    })}
                </div>
                <div style={{margin: "20px 0px", minWidth: "400px", display: "flex", justifyContent: "space-between"}}>
                    <div style={{width:"120px", display: "flex", justifyContent: "space-between"}}>
                        <p className={style.pages} onClick={paging} id="first">처음</p>
                        {page!==0 ?
                            <p className={style.pages} onClick={paging} id="prev">이전</p>:""}
                    </div>
                    <div className={style.pageNumberBox} style={{minWidth: "0px", display: "flex", justifyContent: "space-between"}}>
                        <p onClick={pageNumOnClick} id={page+1+""}>{page+1}</p>
                        <p onClick={pageNumOnClick} id={page+1+""}>{page+2}</p>
                        <p onClick={pageNumOnClick} id={page+1+""}>{page+3}</p>
                        <p onClick={pageNumOnClick} id={page+1+""}>{page+4}</p>
                        <p onClick={pageNumOnClick} id={page+1+""}>{page+5}</p>
                    </div>
                    {pageAttributes!==undefined?
                    <div style={{ width:"120px", display: "flex", justifyContent: "space-between"}}>
                        {page!== pageAttributes.totalPages-1 ?
                            <p className={style.pages} onClick={paging} id="next">다음</p>:
                            ""}
                            <p className={style.pages} onClick={paging} id="last">마지막</p>
                    </div>:""}
                </div>
            </div>
            <Link to="/write/0" style={{position: "relative", bottom: "-50px", marginBottom: "100px"}}>
                <button className={style.toWriteBtn} type="button">글쓰기</button>
            </Link>
        </div>
    )
}