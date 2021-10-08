package com.example.flowerApplication.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import androidx.viewpager.widget.ViewPager
import androidx.viewpager.widget.ViewPager.OnPageChangeListener
import com.example.entities.AboutDataClass
import com.example.entities.OccasionDataClass
import com.example.flowerApplication.R
import com.example.flowerApplication.framework.dbModelFlowers.FlowerEntityDb
import com.example.flowerApplication.framework.dbShopList.ShopListEntityDb
import com.example.flowerApplication.ui.aboutTab.AboutPageAdapter
import com.example.flowerApplication.ui.occasionTab.OccasionAdapterPage
import com.example.flowerApplication.ui.shopListTab.ShopAdapterPage
import com.example.flowerApplication.ui.shopTab.FlowerPageAdapter
import com.example.flowerApplication.viewModel.SharedViewModel

@Suppress("DEPRECATION")
class MainFragment : Fragment(), View.OnClickListener {

    private lateinit var viewPager: ViewPager
    private lateinit var pagerAdapterShop: FlowerPageAdapter
    private lateinit var pagerAdapterAbout: AboutPageAdapter
    private lateinit var occasionAdapterPage: OccasionAdapterPage
    private lateinit var shopAdapterPage: ShopAdapterPage
    private lateinit var viewModel: SharedViewModel
    private lateinit var voiceButton: Button
    private lateinit var shopButton: Button
    private lateinit var aboutButton: Button
    private lateinit var occasionsButton: Button
    private lateinit var shopListBtn: Button
    private lateinit var rightButton: Button
    private lateinit var leftButton: Button
    private lateinit var toolbar: Toolbar
    private var statOfButton: Boolean = true
    private lateinit var swipeLayout: SwipeRefreshLayout
    private lateinit var navController: NavController
    var numberOfItems = 0

    //region onCreate view

    @Suppress("DEPRECATION")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        (requireActivity() as AppCompatActivity).run {
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }

        setHasOptionsMenu(true)
        val view = inflater.inflate(R.layout.main_fragment, container, false)

        navController = Navigation.findNavController(
            requireActivity(), R.id.fragment
        )

        toolbar = view.findViewById<View>(R.id.toolbar4) as Toolbar
        voiceButton = view.findViewById(R.id.voice)
        shopButton = view.findViewById(R.id.shop)
        aboutButton = view.findViewById(R.id.about)
        occasionsButton = view.findViewById(R.id.occasions)
        shopListBtn = view.findViewById(R.id.shopListBtn)
        rightButton = view.findViewById(R.id.buttonRight)
        leftButton = view.findViewById(R.id.buttonLeft)
        viewPager = view.findViewById(R.id.viewPager)
        viewModel = ViewModelProvider(this).get(SharedViewModel::class.java)
        viewModel.refreshData()

        updateButtonBackground()
        setDefaultViewContent()

        pageListener()

        onOptionMenueListener()
        shopButton.setOnClickListener(this)
        aboutButton.setOnClickListener(this)
        occasionsButton.setOnClickListener(this)
        shopListBtn.setOnClickListener(this)

        swipeLayout = view.findViewById(R.id.swipeLayout)
        swipeLayout.setOnRefreshListener {
            viewModel.refreshData()
            resetSideButtonStates()
        }

        voiceButton.setOnClickListener {
            it.post {
                playStopMusic()
            }
        }

        return view
    }

    //endregion

    private fun playStopMusic() {

        if (this.statOfButton) {
            viewModel.playMusic()
            viewModel.buttonState.value = false
        } else {
            viewModel.stopMusic()
            viewModel.buttonState.value = true
        }
    }
    //endregion

    //region When state of pages change

    private fun pageListener() {
        viewPager.addOnPageChangeListener(object : OnPageChangeListener {
            // This method will be invoked when a new page becomes selected.
            override fun onPageSelected(position: Int) {

                if (position == numberOfItems - 1) {
                    rightButton.isVisible = false
                }

                if (position == 0) {
                    leftButton.isVisible = false
                }

                if (position > 0) {
                    leftButton.isVisible = true
                }

                if (position < numberOfItems - 1) {
                    rightButton.isVisible = true
                }
            }

            // This method will be invoked when the current page is scrolled
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
            }

            override fun onPageScrollStateChanged(state: Int) {
            }
        })
    }
    //endregion

    //region Default Content of view
    private fun setDefaultViewContent() {
        viewModel.flowerData.observe(viewLifecycleOwner, {
            pagerAdapterShop = FlowerPageAdapter(
                requireActivity().supportFragmentManager,
                it as ArrayList<FlowerEntityDb>
            )
            viewPager.adapter = pagerAdapterShop
            swipeLayout.isRefreshing = false

            numberOfItems = pagerAdapterShop.count

        })

    }
//endregion

    //region Update background of all buttons

    private fun updateButtonBackground() {

        setStateOfButtons(
            getString(R.string.shop),
            R.drawable.green_shop_btn,
            "",
            R.drawable.black_about_btn,
            "",
            R.drawable.black_occasions_btn,
            "",
            R.drawable.shop_list_btn
        )
        // Update background state of music button
        viewModel.buttonState.observe(viewLifecycleOwner, {
            if (it) {
                voiceButton.setCompoundDrawablesWithIntrinsicBounds(
                    0,
                    R.drawable.black_voice_btn,
                    0,
                    0
                )
            } else {
                voiceButton.setCompoundDrawablesWithIntrinsicBounds(
                    0,
                    R.drawable.green_voice_btn,
                    0,
                    0
                )
            }
            statOfButton = it
        })
    }

    //endregion

    //region Events for 4 button in bottom of screen
    override fun onClick(v: View?) {

        when (v!!.id) {
            R.id.shop -> {
                stateSideAndSwipeBtns(rightBtn = true, swipeLayout = true)

                setStateOfButtons(
                    getString(R.string.shop), R.drawable.green_shop_btn,
                    "", R.drawable.black_about_btn,
                    "", R.drawable.black_occasions_btn,
                    "", R.drawable.shop_list_btn
                )

                setDefaultViewContent()
                pageListener()
            }

            R.id.about -> {
                stateSideAndSwipeBtns(rightBtn = true, swipeLayout = false)

                setStateOfButtons(
                    "", R.drawable.black_shop_btn,
                    getString(R.string.about), R.drawable.green_about_btn,
                    "", R.drawable.black_occasions_btn,
                    "", R.drawable.shop_list_btn
                )

                viewPager.adapter = null
                viewModel.aboutData.observe(viewLifecycleOwner, {
                    pagerAdapterAbout = AboutPageAdapter(
                        requireActivity().supportFragmentManager,
                        it as ArrayList<AboutDataClass>
                    )
                    viewPager.adapter = pagerAdapterAbout

                    numberOfItems = pagerAdapterAbout.count
                })
            }

            R.id.occasions -> {
                stateSideAndSwipeBtns(rightBtn = false, swipeLayout = false)

                setStateOfButtons(
                    "", R.drawable.black_shop_btn,
                    "", R.drawable.black_about_btn,
                    getString(R.string.occasions), R.drawable.green_occasions_btn,
                    "", R.drawable.shop_list_btn
                )

                val onepageOfOccasions = OccasionDataClass(1)
                val myarrayList = ArrayList<OccasionDataClass>()
                myarrayList.add(onepageOfOccasions)
                occasionAdapterPage = OccasionAdapterPage(
                    requireActivity().supportFragmentManager,
                    myarrayList
                )
                viewPager.adapter = occasionAdapterPage

            }
            R.id.shopListBtn -> {

                stateSideAndSwipeBtns(rightBtn = false, swipeLayout = false)

                setStateOfButtons(
                    "", R.drawable.black_shop_btn,
                    "", R.drawable.black_about_btn,
                    "", R.drawable.black_occasions_btn,
                    getString(R.string.shop_list), R.drawable.shop_list_btn_green
                )

                val onepageOfOccasions = ShopListEntityDb(1, "", "", 0.0)
                val myarrayList = ArrayList<ShopListEntityDb>()
                myarrayList.add(onepageOfOccasions)
                shopAdapterPage = ShopAdapterPage(
                    requireActivity().supportFragmentManager,
                    myarrayList
                )
                viewPager.adapter = shopAdapterPage

            }
            else -> {
            }
        }
    }

    private fun stateSideAndSwipeBtns(rightBtn: Boolean, swipeLayout: Boolean) {
        leftButton.isVisible = false
        rightButton.isVisible = rightBtn
        this.swipeLayout.isEnabled = swipeLayout
    }

    private fun setStateOfButtons(
        s1: String,
        btn1State: Int,
        s2: String,
        blackAboutBtn: Int,
        s3: String,
        blackOccasionsBtn: Int,
        s4: String,
        shopListBtn: Int
    ) {

        s1.let { shopButton.text = it }
        shopButton.setCompoundDrawablesWithIntrinsicBounds(
            0,
            btn1State,
            0,
            0
        )
        s2.let { aboutButton.text = it }
        aboutButton.setCompoundDrawablesWithIntrinsicBounds(
            0,
            blackAboutBtn,
            0,
            0
        )
        s3.let { occasionsButton.text = it }
        occasionsButton.setCompoundDrawablesWithIntrinsicBounds(
            0,
            blackOccasionsBtn,
            0,
            0
        )
        s4.let { this.shopListBtn.text = it }
        this.shopListBtn.setCompoundDrawablesWithIntrinsicBounds(
            0,
            shopListBtn,
            0,
            0
        )
    }
    //endregion

    //region Events for Options menu in toolbar
    private fun onOptionMenueListener() {
        toolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.action_settings -> {
                    navController.navigate(R.id.action_to_settingsFragment)
                    true
                }
                R.id.action_getHelp -> {
                    navController.navigate(R.id.nav_main_to_help)
                    true
                }
                else -> {
                    true
                }
            }
        }
    }
    //endregion

    //region onViewCreated

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.flowerData.observe(viewLifecycleOwner, {
            pagerAdapterShop = FlowerPageAdapter(
                requireActivity().supportFragmentManager,
                it as ArrayList<FlowerEntityDb>
            )
            viewPager.adapter = pagerAdapterShop

            resetSideButtonStates()

        })

        rightButton.setOnClickListener {

            if (viewPager.currentItem < numberOfItems - 1) {
                viewPager.currentItem = viewPager.currentItem + 1
            }
        }

        leftButton.setOnClickListener {

            if (viewPager.currentItem > 0) {
                viewPager.currentItem = viewPager.currentItem - 1
            }
        }

    }

    //endregion

    //region set state for button pages

    private fun resetSideButtonStates() {
        leftButton.isVisible = false
        rightButton.isVisible = true
    }
    //endregion
}