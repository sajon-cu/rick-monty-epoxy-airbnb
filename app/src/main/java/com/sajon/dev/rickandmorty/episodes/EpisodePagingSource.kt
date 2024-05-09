package com.sajon.dev.rickandmorty.episodes

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.sajon.dev.rickandmorty.domain.mappers.EpisodeMapper
import com.sajon.dev.rickandmorty.network.NetworkLayer

class EpisodePagingSource : PagingSource<Int, EpisodesUiModel>() {
    override fun getRefreshKey(state: PagingState<Int, EpisodesUiModel>): Int? {
        // Try to find the page key of the closest page to anchorPosition, from
        // either the prevKey or the nextKey, but you need to handle nullability
        // here:
        //  * prevKey == null -> anchorPage is the first page.
        //  * nextKey == null -> anchorPage is the last page.
        //  * both prevKey and nextKey null -> anchorPage is the initial page, so
        //    just return null.
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, EpisodesUiModel> {
        val pageIndex = params.key ?: 1
        val prevIndex = if(pageIndex == 1) null else pageIndex - 1

        val pageRequest = NetworkLayer.apiClient.getEpisodesPage(pageIndex)
        pageRequest.error?.let { return LoadResult.Error(it) }

        return LoadResult.Page(
            data = pageRequest.body.results.map { response ->
                EpisodesUiModel.Item(EpisodeMapper.buildFrom(response))
            },
            prevKey = prevIndex,
            nextKey = getPageIndexFromNext(pageRequest.body.info.next)
        )
    }

    private fun getPageIndexFromNext(next: String?): Int? {
        return next?.split("?page=")?.get(1)?.toInt()
    }
}