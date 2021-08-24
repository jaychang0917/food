import 'package:flutter/material.dart';
import '../core/food_colors.dart';
import '../widgets/food_text.dart';

class FoodHeader extends StatelessWidget {
  FoodHeader._(this._navigationType, {
    required this.onNavigationIconTapped,
    this.title = ''
  });

  FoodHeader.back({
    required VoidCallback onNavigationIconTapped,
    String title = ''
  }) : this._(
    NavigationType.back,
    onNavigationIconTapped: onNavigationIconTapped,
    title: title);

  FoodHeader.close({
    required VoidCallback onNavigationIconTapped,
    String title = ''
  }) : this._(
    NavigationType.close,
    onNavigationIconTapped: onNavigationIconTapped,
    title: title);

  final NavigationType _navigationType;
  final VoidCallback onNavigationIconTapped;
  final String title;

  @override
  Widget build(BuildContext context) {
    return AppBar(
      toolbarHeight: 56,
      title: FoodText.h6(title),
      elevation: 0,
      backwardsCompatibility: false,
      backgroundColor: FoodColors.secondary,
      foregroundColor: FoodColors.onSecondary,
      leading: IconButton(
        onPressed: onNavigationIconTapped,
        icon: Icon(_navigationType == NavigationType.back ? Icons.arrow_back : Icons.clear),
        splashRadius: 24
      )
    );
  }
}

enum NavigationType { back, close }
