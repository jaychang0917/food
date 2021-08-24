import 'package:base/base.dart';
import 'package:flutter/material.dart';
import 'package:home/home.dart';
import 'package:order/order.dart';
import 'package:profile/profile.dart';
import 'package:search/search.dart';

import 'main_bloc.dart';
import 'main_event.dart';
import 'main_state.dart';

class MainPage extends StatelessWidget {
  const MainPage({
    Key? key,
    required this.activeTab
  }): super(key: key);

  final AppTab activeTab;

  @override
  Widget build(BuildContext context) {
    return BlocProvider(
      create: (context) {
        return MainBloc(activeTab: activeTab);
      },
      child: BlocBuilder<MainBloc, MainState>(
        builder: (context, state) => Column(
          children: [
            Expanded(child: _activePage(state)),
            _bottomTabSelector(context, state)
          ],
        ),
      ),
    );
  }

  Widget _activePage(MainState state) {
    Widget page;
    if (state.activeTab == AppTab.home) {
      page = HomePage();
    } else if (state.activeTab == AppTab.search) {
      page = SearchPage();
    } else if (state.activeTab == AppTab.order) {
      page = OrderPage();
    } else if (state.activeTab == AppTab.profile) {
      page = ProfilePage();
    } else {
      throw Exception('No such page for tab: ${state.activeTab}.');
    }

    return page;
  }

  Widget _bottomTabSelector(BuildContext context, MainState state) {
    Icon _icon(AppTab tab) {
      final Icon icon;
      if (tab == AppTab.home) {
        icon = Icon(Icons.home);
      } else if (tab == AppTab.search) {
        icon = Icon(Icons.search);
      } else if (tab == AppTab.order) {
        icon = Icon(Icons.assignment);
      } else if (tab == AppTab.profile) {
        icon = Icon(Icons.person);
      } else {
        throw Exception('No such tab: $tab.');
      }
      return icon;
    }
    final items = AppTab.values.map((tab) => BottomNavigationBarItem(
      icon: _icon(tab),
      tooltip: '',
      label: ''
    )).toList();
    return BottomNavigationBar(
      backgroundColor: FoodColors.secondary,
      type: BottomNavigationBarType.fixed,
      unselectedItemColor: FoodColors.icon,
      selectedItemColor: FoodColors.primary,
      items: items,
      currentIndex: AppTab.values.indexOf(state.activeTab),
      // selectedItemColor: FoodColors.primary,
      showSelectedLabels: false,
      showUnselectedLabels: false,
      onTap: (index) => BlocProvider.of<MainBloc>(context).add(
        TabChanged(AppTab.values[index])
      ),
    );
  }
}