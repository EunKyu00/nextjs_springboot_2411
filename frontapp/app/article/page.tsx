"use client";

import { useParams } from "next/navigation";

export default async function Article() {
  await fetch("http://localhost:8091/api/v1/articles");
  return (
    <>
      <div>게시판</div>
    </>
  );
}
