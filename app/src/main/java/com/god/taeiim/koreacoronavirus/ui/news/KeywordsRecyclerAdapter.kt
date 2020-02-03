package com.god.taeiim.koreacoronavirus.ui.news

import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.databinding.library.baseAdapters.BR
import com.god.taeiim.koreacoronavirus.base.BaseRecyclerAdapter
import com.god.taeiim.koreacoronavirus.base.BaseRecyclerViewHolder

class KeywordsRecyclerAdapter<ITEM : Any, B : ViewDataBinding>(
    layout: Int,
    bindingVariableId: Int,
    val vm: NewsViewModel
) : BaseRecyclerAdapter<ITEM, B>(layout, bindingVariableId) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseRecyclerViewHolder<B> {

        return BaseRecyclerViewHolder(
            layoutResId,
            parent = parent,
            bindingVariableId = bindingVariableId
        )
    }

    override fun onBindViewHolder(holder: BaseRecyclerViewHolder<B>, position: Int) {
        super.onBindViewHolder(holder, position)
        holder.binding.setVariable(BR.vm, vm)
    }

}
