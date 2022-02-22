import 'package:base/base.dart';
import 'package:flutter/material.dart';
import 'package:lottie/lottie.dart';
import 'package:root/src/widgets/retry_dialog.dart';

import 'root_bloc.dart';
import 'root_event.dart';
import 'root_state.dart';

class RootPage extends StatefulWidget {
  const RootPage({Key? key}) : super(key: key);

  @override
  _RootPageState createState() => _RootPageState();
}

class _RootPageState extends State<RootPage> with TickerProviderStateMixin {
  late AnimationController _animationController;

  @override
  void initState() {
    super.initState();
    _animationController = AnimationController(vsync: this);
  }

  @override
  void dispose() {
    _animationController.dispose();
    super.dispose();
  }

  @override
  Widget build(BuildContext context) {
    return BlocProvider(
      create: (context) {
        final sessionRepository = RepositoryProvider.of<SessionRepository>(context);
        return RootBloc(sessionRepository: sessionRepository)..add(Initialized());
      },
      child: BlocConsumer<RootBloc, RootState>(
        listener: (context, state) {
          if (state.navigation is HomeNavigation) {
            _animate(() => _goToHomePage(context));
          } else if (state.navigation is LandingNavigation) {
            _animate(() => _goToLandingPage(context));
          } else if (state.error != null) {
            _showRetryDialog(context);
          }
        },
        builder: (context, state) {
          return Container(
            color: Colors.black,
            alignment: Alignment.center,
            child: Lottie.asset(
              'assets/logo.json',
              package: 'root',
              width: 300,
              height: 300,
              controller: _animationController,
              onLoaded: (composition) {
                _animationController.duration = composition.duration;
              }
            ),
          );
        },
      ),
    );
  }

  void _animate(void Function() completed) {
    _animationController.forward(from: 15.0 / 100.0).then((value) => completed());
  }

  void _showRetryDialog(BuildContext context) {
    final dialog = RetryDialog(onRetryButtonTapped: () {
      BlocProvider.of<RootBloc>(context).add(Initialized());
      Navigator.of(context).pop();
    });
    dialog.show(context);
  }

  void _goToLandingPage(BuildContext context) {
    Navigator.pushNamedAndRemoveUntil(context, AppRoutes.landing, (_) => false);
  }

  void _goToHomePage(BuildContext context) {
    Navigator.pushNamedAndRemoveUntil(context, AppRoutes.main, (_) => false);
  }
}
