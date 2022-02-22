
import 'dart:collection';

import 'package:base/base.dart';
import 'package:flutter/material.dart';

import '../country_state.dart';
import 'CountryItem.dart';
import 'SectionedCountryItem.dart';

class CountryList extends StatefulWidget {
  const CountryList({
    Key? key,
    required this.items,
    required this.selected,
    required this.isListSectionHeaderVisible,
    this.onItemSelected
  }) : super(key: key);

  final List<CountryItemState> items;

  final CountryItemState selected;

  final bool isListSectionHeaderVisible;

  final ValueChanged<Country>? onItemSelected;

  @override
  CountryListState createState() => CountryListState();
}

class CountryListState extends State<CountryList> {
  late ScrollController _scrollController;

  @override
  void initState() {
    super.initState();
    _scrollController = ScrollController();
  }

  @override
  void dispose() {
    super.dispose();
    _scrollController.dispose();
  }

  @override
  Widget build(BuildContext context) {
    final items = widget.items;
    return ListView.custom(
      controller: _scrollController,
      childrenDelegate: SliverChildBuilderDelegate(
              (BuildContext context, int index) {
            final item = items[index];
            final countryItem = CountryItem(
                key: ValueKey(item),
                item: item,
                isSelected: item.country == widget.selected.country,
                onTapped: (item) => widget.onItemSelected?.call(item)
            );
            return SectionedCountryItem(
                headerText: _headerText(index),
                countryItem: countryItem
            );
          },
          childCount: items.length,
          findChildIndexCallback: (Key key) {
            final valueKey = key as ValueKey<CountryItemState>;
            final data = valueKey.value;
            return items.indexOf(data);
          }
      ),
    );
  }

  String? _headerText(int itemIndex) {
    final preItemIndex = (itemIndex - 1).clamp(0, widget.items.length - 1);
    final item = widget.items[itemIndex];
    final preItem = widget.items[preItemIndex];
    final hasSameInitial = preItem.name[0] == item.name[0];
    final hasHeader = widget.isListSectionHeaderVisible && (!hasSameInitial || itemIndex == 0);
    return hasHeader ? item.name[0] : null;
  }

  void scrollToSection(String header) {
    // Group letter initial : group height
    // e.g. {'A': 200, 'B': 100, 'C': 50}
    final grouped = groupBy<CountryItemState, String>(widget.items, (e) => e.name[0])
      .map<String, double>((key, value) {
        final itemTotalHeight = value.length * CountryItem.height;
        final dividerTotalHeight = (value.length - 1) * SectionedCountryItem.dividerHeight;
        return MapEntry(key, itemTotalHeight + dividerTotalHeight + SectionedCountryItem.headerHeight);
      });
    final sortedGroup = SplayTreeMap<String, double>.from(grouped);

    final keys = sortedGroup.keys.toList();
    final values = sortedGroup.values.toList();
    final beforeN = keys.indexOf(header);
    // Accumulates height of previous N entry's value
    // e.g. A -> 0, B -> 200, C -> 300
    final accumulatedHeight;
    if (beforeN <= 0) {
      accumulatedHeight = 0;
    } else {
      accumulatedHeight = values.sublist(0, beforeN).reduce((value, element) => value + element);
    }
    
    _scrollController.jumpTo(accumulatedHeight);
  }
}
