"use client";
import { useState, useEffect } from "react";
export default function Login() {
  const [user, setUser] = useState({ username: "", passsword: "" });
  const handleSubmit = async (e) => {
    e.preventDefault();
    const response = await fetch(`http://localhost:8091/api/v1/members/login`, {
      method: "POST",
      credentials: "include", // 인증 정보를 함께 보내는 경우
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify(user),
    });
    if (response.ok) {
      alert("success");
    } else {
      alert("fail ");
    }
  };
  const handleChange = (e) => {
    const { name, value } = e.target;
    setUser({ ...user, [name]: value });
    console.log({ ...user, [name]: value });
  };
  return (
    <>
      <h4>로그인</h4>
      <form onSubmit={handleSubmit}>
        <input
          type="text"
          name="username"
          onChange={handleChange}
          placeholder="아이디"
        />
        <input
          type="password"
          name="password"
          onChange={handleChange}
          placeholder="비밀번호"
        />
        <input type="submit" value="로그인" />
      </form>
    </>
  );
}
