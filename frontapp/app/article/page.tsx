"use client";

import { useEffect, useState } from "react";

export default function ArticleDetail() {
  const [articles, setArticles] = useState([]);

  useEffect(() => {
    getData();
  }, []);

  const getData = async () => {
    const result = await fetch("http://localhost:8091/api/v1/articles").then(
      (row) => row.json()
    );
    setArticles(result.data.articles);
    console.log(result.data.articles);
  };

  return (
    <>
      <h4>번호 / 제목 / 생성일</h4>
      {articles.length == 0 ? (
        <p>현재 게시물이 없습니다.</p>
      ) : (
        <ul>
          {articles.map((article) => (
            <li key={article.id}>
              {article.id}/{article.subject}/{article.createDate}
            </li>
          ))}
        </ul>
      )}
    </>
  );
}
