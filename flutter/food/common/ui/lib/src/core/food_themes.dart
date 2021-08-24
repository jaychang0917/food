import 'package:flutter/material.dart';

abstract class FoodThemes {
  static final ThemeData _base = ThemeData(
    fontFamily: 'Roboto',
  );

  static final ThemeData light = _base.copyWith(brightness: Brightness.light);

  static final ThemeData dark = _base.copyWith(brightness: Brightness.dark);
}
