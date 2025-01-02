"use client";

import Link from "next/link";
import { useEffect, useState } from "react";

export default function ArticleDetail() {
  const [articles, setArticles] = useState([]);

  useEffect(() => {
    fetchArticles();
  }, []);

  const fetchArticles = async () => {
    const result = await fetch("http://localhost:8091/api/v1/articles")
      .then((result) => result.json())
      .then((result) => setArticles(result.data.articles));
  };

  const handleDelete = async (id) => {
    const response = await fetch(
      `http://localhost:8091/api/v1/articles/${id}`,
      {
        method: "DELETE",
      }
    );

    if (response.ok) {
      alert("success");
      fetchArticles();
    } else {
      alert(`fail`);
    }
  };

  return (
    <>
      <ArticleForm fetchArticles={fetchArticles} />
      <h4>번호 / 제목 / 생성일</h4>
      {articles.length == 0 ? (
        <p>현재 게시물이 없습니다.</p>
      ) : (
        <ul>
          {articles.map((article) => (
            <li key={article.id}>
              {article.id}/
              <Link href={`/article/${article.id}`}>{article.subject}</Link> /
              {article.createDate}
              <button onClick={() => handleDelete(article.id)}>삭제</button>
            </li>
          ))}
        </ul>
      )}
    </>
  );
}

function ArticleForm({ fetchArticles }) {
  const [article, setArticle] = useState({ subject: "", content: "" });

  const handleSubmit = async (e) => {
    e.preventDefault();

    const response = await fetch("http://localhost:8091/api/v1/articles", {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify(article),
    });

    if (response.ok) {
      alert("success");
      fetchArticles();
    } else {
      alert("fail");
    }
  };

  const handleChange = (e) => {
    const { name, value } = e.target;
    setArticle({ ...article, [name]: value });
    console.log({ ...article, [name]: value });
  };

  return (
    <>
      <h4>게시물 작성</h4>
      <form onSubmit={handleSubmit}>
        <label>
          제목 :
          <input type="text" name="subject" onChange={handleChange} />
        </label>
        <br />
        <label>
          내용 :
          <input type="text" name="content" onChange={handleChange} />
        </label>
        <button type="submit">등록</button>
      </form>
    </>
  );
}
