# Swift UI Animations

### Animation Timing
- <b>.linear:</b> This transitions the attribute from the start to end value evenly over time. This is a good timing curve for repeating animations, but it doesn’t look as natural as the eased functions.

- <b>.easeIn:</b> An eased-in animation starts off slow and picks up speed over time. This is good for animations that start from a resting point and finish off-screen.

- <b>.easeOut:</b> Eased-out animations start fast and end slow. This is good for animating something coming to a steady state or final position.

- <b>.easeInOut:</b> Ease in and out curves combine both easeIn and easeOut. This is good for animations that start in one steady spot and end at another equilibrium. This is best for most applications. That is why this is timing curve used by .default.

- <b>.timingCurve:</b> This allows you to specify a custom timing curve. This is rarely needed.

## Opacity Animation
It enables the transformation of visual objects into transparent ones. 0 makes it invisible at all, 1 makes it fully visible.
```swift
struct TestView: View {
    
    @State private var opacity : Double = 1.0
    
    var body: some View {
        VStack {
            Image("horizontal_gozen_pass_logo")
                .frame(width: 200,
                        height: 200,
                        alignment: .center)
                .opacity(opacity)
                
            Button {
                withAnimation(.easeInOut(duration: 1.0)){
                    if opacity == 0.0 {
                        opacity = 1.0
                    } else if opacity == 1.0 {
                        opacity = 0.0
                    }
                }
            } label: {
                Text("START ANIMATE")
                    .padding(.all,10)
                    .foregroundColor(Color.black)
                    .background {
                        RoundedRectangle(cornerRadius: 15)
                            .stroke(Color.black)
                    }
            }
        }.frame(width: UIScreen.main.bounds.width,
                height: UIScreen.main.bounds.height,
                alignment: .center)
        .background(Color.green)
    }
}
```
## Scale Animation
An animation format used to change the size of a visual object.
```swift
struct TestView: View {
    
    @State private var scale : Bool = true
    
    var body: some View {
        VStack {
            Image("horizontal_gozen_pass_logo")
                .frame(width: 200,
                        height: 200,
                        alignment: .center)
                .scaleEffect(scale ? 1.0 : 0.0)
                
            Button {
                withAnimation(.easeInOut(duration: 1.0)){
                    if scale {
                        scale = false
                    } else {
                        scale = true
                    }
                }
            } label: {
                Text("START ANIMATE")
                    .padding(.all,10)
                    .foregroundColor(Color.black)
                    .background {
                        RoundedRectangle(cornerRadius: 15)
                            .stroke(Color.black)
                    }
            }
        }.frame(width: UIScreen.main.bounds.width,
                height: UIScreen.main.bounds.height,
                alignment: .center)
        .background(Color.green)
    }
}
```

## Rotate Animation
They are animations created by rotating the visual object.
```swift
struct AnimationView: View {
    
    @State private var rotate : Bool = true
    
    var body: some View {
        VStack {
            Image("horizontal_gozen_pass_logo")
                .frame(width: 200,
                        height: 200,
                        alignment: .center)
                .rotationEffect(Angle(degrees: rotate ? 0 : 360.0))
                
            Button {
                withAnimation(.easeInOut(duration: 1.0)){
                    if rotate {
                        rotate = false
                    } else {
                        rotate = true
                    }
                }
            } label: {
                Text("START ANIMATE")
                    .padding(.all,10)
                    .foregroundColor(Color.black)
                    .background {
                        RoundedRectangle(cornerRadius: 15)
                            .stroke(Color.black)
                    }
            }
        }.frame(width: UIScreen.main.bounds.width,
                height: UIScreen.main.bounds.height,
                alignment: .center)
        .background(Color.green)
    }
}
```

## Transform Animation
An animation method used to change the positions of visual objects.
```swift
struct AnimationView: View {
    
    @State private var transform : Bool = true
    @State private var x : Double = 0.0
    @State private var y : Double = 0.0
    
    var body: some View {
        VStack {
            Image("horizontal_gozen_pass_logo")
                .frame(width: 200,
                        height: 200,
                        alignment: .center)
                .transformEffect(CGAffineTransform(translationX: self.x, y: self.y))
                
            Button {
                withAnimation(.easeInOut(duration: 1.0)){
                    if transform {
                        transform = false
                        self.x = -100
                        self.y = -200
                    } else {
                        transform = true
                        self.x = 100
                        self.y = -100
                    }
                }
            } label: {
                Text("START ANIMATE")
                    .padding(.all,10)
                    .foregroundColor(Color.black)
                    .background {
                        RoundedRectangle(cornerRadius: 15)
                            .stroke(Color.black)
                    }
            }
        }.frame(width: UIScreen.main.bounds.width,
                height: UIScreen.main.bounds.height,
                alignment: .center)
        .background(Color.green)
    }
}
```
