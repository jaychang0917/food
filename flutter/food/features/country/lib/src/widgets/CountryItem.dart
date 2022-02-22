import 'dart:convert';

import 'package:base/base.dart';
import 'package:country/src/country_state.dart';
import 'package:flutter/material.dart';

class CountryItem extends StatelessWidget {
  const CountryItem({
    Key? key,
    required this.item,
    this.isSelected = false,
    this.onTapped
  }) : super(key: key);

  final CountryItemState item;

  final bool isSelected;
  
  final void Function(Country)? onTapped;

  static const double height = 56;

  @override
  Widget build(BuildContext context) {
    return SizedBox(
      height: height,
      child: ListTile(
        title: FoodText.subtitle1(item.name),
        leading: Image.memory(base64.decode(item.flag), width: 32, height: 23),
        trailing: isSelected ? Icon(Icons.check, color: FoodColors.accent) : null,
        onTap: onTapped != null ? () => onTapped!(item.country) : null,
      ),
    );
  }
}
