package com.example.simple_shop_aos.product

import android.graphics.Color
import android.os.Build
import android.view.Gravity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import com.example.simple_shop_aos.R
import com.example.simple_shop_aos.common.Prefs
import com.example.simple_shop_aos.signin.SigninActivity
import com.example.simple_shop_aos.view.borderBottom
import com.google.android.material.navigation.NavigationView
import org.jetbrains.anko.*
import org.jetbrains.anko.appcompat.v7.toolbar
import org.jetbrains.anko.design.floatingActionButton
import org.jetbrains.anko.design.navigationView
import org.jetbrains.anko.sdk25.coroutines.onClick
import org.jetbrains.anko.support.v4.drawerLayout

class ProductMainUI(
    private val viewModel: ProductMainViewModel
) : AnkoComponent<ProductMainActivity>,
    NavigationView.OnNavigationItemSelectedListener { //1

    lateinit var drawerLayout: DrawerLayout
    lateinit var navigationView: NavigationView //2
    lateinit var toolBar: Toolbar

    override fun createView(ui: AnkoContext<ProductMainActivity>) =
        ui.drawerLayout {
            drawerLayout = this

            frameLayout {
                verticalLayout {
                    toolBar = toolbar {
                        title = "송꼬"
                        bottomPadding = dip(1)
                        background = borderBottom(width = dip(1))
                        menu.add("검색")
                            .setIcon(R.drawable.magnify)
                            .setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS)
                    }.lparams(matchParent, wrapContent)

//                view {
//                    backgroundColor = Color.parseColor("#DDDDDD")
//                }.lparams(matchParent, dip(1))
                }.lparams(matchParent, matchParent)


                floatingActionButton {
                    imageResource = R.drawable.ic_add
                    onClick { viewModel.openRegistrationActivity() }
                }.lparams {
                    bottomMargin = dip(20)
                    marginEnd = dip(20)
                    gravity = Gravity.END or Gravity.BOTTOM
                }

            }
            navigationView = navigationView {//3
                // TODO let인지 확인이 필요 p163
                ProductMainNavHeader()
                    .createView(AnkoContext.create(context, this))
                    .run(::addHeaderView)

                //4
                menu.apply {
                    add(Menu.NONE, MENU_ID_INQUIRY, Menu.NONE, "내 문의").apply {
                        setIcon(R.drawable.ic_chat)
                    }

                    add(Menu.NONE, MENU_ID_LOGOUT, Menu.NONE, "로그아웃").apply {
                        setIcon(R.drawable.ic_signout)
                    }
                }

                //5
                setNavigationItemSelectedListener(this@ProductMainUI)

            }.lparams(wrapContent, matchParent) {
                gravity = Gravity.START
            }
        }

    //6
    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            MENU_ID_INQUIRY -> {
                viewModel.toast("내 문의")
            }
            MENU_ID_LOGOUT -> {
                Prefs.token = null
                Prefs.refreshToken = null
                viewModel.startActivityAndFinish<SigninActivity>()
            }
        }

        drawerLayout.closeDrawer(navigationView)
        return true
    }

    //7
    companion object {
        private const val MENU_ID_INQUIRY = 1
        private const val MENU_ID_LOGOUT = 2
    }
}