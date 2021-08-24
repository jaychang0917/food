
import 'package:base/base.dart';
import 'package:flutter/material.dart';

class SectionIndex extends StatefulWidget {
  SectionIndex({Key? key, this.onLetterSelected}) : super(key: key);

  final ValueChanged<String>? onLetterSelected;

  @override
  _SectionIndexState createState() => _SectionIndexState();
}

class _SectionIndexState extends State<SectionIndex> with WidgetsBindingObserver {
  static const letters = 'ABCDEFGHIJKLMNOPQRSTUVWXYZ';

  int _currentLetterPosition = 0;

  int _preLetterPosition = -1;

  bool _isVisible = true;

  @override
  void initState() {
    super.initState();
    WidgetsBinding.instance!.addObserver(this);
  }

  @override
  void dispose() {
    WidgetsBinding.instance!.removeObserver(this);
    super.dispose();
  }

  @override
  void didChangeMetrics() {
    final isKeyboardVisible = WidgetsBinding.instance!.window.viewInsets.bottom != 0;
    setState(() {
      _isVisible = !isKeyboardVisible;
    });
  }

  @override
  Widget build(BuildContext context) {
    return _isVisible ? Listener(
      behavior: HitTestBehavior.opaque, // prevent beneath widget to receive event
      onPointerDown: (e) => updatePosition(context, e),
      onPointerMove: (e) => updatePosition(context, e),
      child: Wrap(
        direction: Axis.vertical,
        children: letters.split('').map(_letter).toList(),
      ),
    ) : Container();
  }

  Widget _letter(String letter) => Padding(
      padding: const EdgeInsets.only(left:FoodSpacings.small, right: FoodSpacings.xxsmall),
      child: FoodText.caption(letter).bold()
  );

  void updatePosition(BuildContext context, PointerEvent event) {
    final y = event.localPosition.dy;
    final height = context.size!.height;
    final tempPosition = (y / height * letters.length).toInt();
    _currentLetterPosition = tempPosition.clamp(0, letters.length - 1);
    if (_currentLetterPosition != _preLetterPosition) {
      widget.onLetterSelected?.call(letters[_currentLetterPosition]);
      _preLetterPosition = _currentLetterPosition;
    }
  }
}
