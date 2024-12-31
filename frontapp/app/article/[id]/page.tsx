"use client";

import { useParams } from "next/navigation";

export default async function ArticleDetail() {
  await fetch("http/localhost:8091/api/v1/articles");

  return <></>;
}
