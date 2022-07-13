# Swift UI Animations
## Opacity Animation
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
