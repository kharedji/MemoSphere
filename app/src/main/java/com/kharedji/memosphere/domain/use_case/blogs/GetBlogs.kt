package com.kharedji.memosphere.domain.use_case.blogs

import com.kharedji.memosphere.domain.repository.BlogRepository

class GetBlogs(
    private val repository: BlogRepository
) {
}