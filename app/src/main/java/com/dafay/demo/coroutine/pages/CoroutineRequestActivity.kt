package com.dafay.demo.coroutine.pages

import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.dafay.demo.coroutine.R
import com.dafay.demo.coroutine.databinding.ActivityCoroutineRequestBinding
import com.dafay.demo.coroutine.databinding.ActivityRxJavaRequestBinding
import com.dafay.demo.coroutine.pages.adapter.PhotoAdapter
import com.dafay.demo.lib.base.ui.base.BaseActivity
import com.dafay.demo.lib.base.utils.dp2px
import com.example.demo.biz.base.widgets.GridMarginDecoration

class CoroutineRequestActivity : BaseActivity(R.layout.activity_coroutine_request) {
    override val binding: ActivityCoroutineRequestBinding by viewBinding()
    private val viewModel by lazy { ViewModelProvider(this).get(CoroutinePhotosViewModel::class.java) }

    private lateinit var photoAdapter: PhotoAdapter
    override fun initViews() {
        super.initViews()
        initRecyclerView()
    }

    private fun initRecyclerView() {
        photoAdapter = PhotoAdapter()
        binding.rvRecyclerview.addItemDecoration(GridMarginDecoration(4.dp2px, 4.dp2px, 4.dp2px, 4.dp2px))
        binding.rvRecyclerview.layoutManager = StaggeredGridLayoutManager(2, RecyclerView.VERTICAL)
        binding.rvRecyclerview.adapter = photoAdapter
    }

    override fun initObserver() {
        super.initObserver()
        viewModel.photosLiveData.observe(this) {
            photoAdapter.setDatas(it)
        }
    }

    override fun initializeData() {
        super.initializeData()
        viewModel.queryPhotos()
    }

}