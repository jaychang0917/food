
import 'package:base/base.dart';
import 'package:flutter/material.dart';

class SectionedCountryItem extends StatelessWidget {
  const SectionedCountryItem({
    Key? key,
    this.headerText,
    required this.countryItem,
  }) : super(key: key);

  final String? headerText;

  final Widget countryItem;

  static const double headerHeight = 56;
  static const double dividerHeight = 1;

  @override
  Widget build(BuildContext context) {
    return Column(
      mainAxisSize: MainAxisSize.min,
      children: [
        if (headerText != null) Container(
          height: headerHeight,
          color: FoodColors.background,
          padding: const EdgeInsets.symmetric(horizontal: FoodSpacings.normal, vertical: FoodSpacings.small),
          alignment: Alignment.bottomLeft,
          child: FoodText.body1(headerText!).withColor(FoodColors.onBackgroundPrimary),
        ),
        if (headerText == null) Container(
            height: dividerHeight,
            color: FoodColors.background
        ),
        countryItem,
      ],
    );
  }
}

